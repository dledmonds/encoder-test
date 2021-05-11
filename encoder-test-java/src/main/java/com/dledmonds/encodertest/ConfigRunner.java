package com.dledmonds.encodertest;

import com.dledmonds.encodertest.utils.CharacterUtils;
import com.dledmonds.encodertest.utils.ClassLoaderFactory;
import com.dledmonds.encodertest.utils.FileUtils;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Method;
import java.net.URL;
import java.util.Date;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.StringTokenizer;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Runs a configuration test
 *
 * @author dledmonds
 */
public class ConfigRunner {

    // local maven repository, assumed to be ~/.m2/repository
    private static File LOCAL_MAVEN_REPO = new File(
            System.getProperty("user.home"), ".m2" + File.separator + "repository");

    // local cache for libraries not already in local maven repository
    private static File LOCAL_CACHE = new File(System.getProperty("user.home"), ".encoder-test");

    // remote maven repository to download required libraries from
    private static String REMOTE_MAVEN_REPO = "https://repo1.maven.org/maven2/";

    private final Logger log = Logger.getLogger(this.getClass().getName());

    private File baseDir;

    // cache these to speed things up, reflection is slow
    private Map<String, Method> cachedMethods;

    ConfigRunner(File baseDir) {
        this.baseDir = baseDir;
        this.cachedMethods = new HashMap<>();
    }

    void run(List<TestConfig> configs) {
        ObjectMapper mapper = new ObjectMapper().enable(SerializationFeature.INDENT_OUTPUT);

        for (TestConfig config : configs) {
            try {
                TestResult result = runConfig(config);
                result.addProperty("os.name", System.getProperty("os.name"));
                result.addProperty("java.version", System.getProperty("java.version"));
                result.setGeneratedDate(new Date());
                File resultFile = new File(this.baseDir, FileUtils.getSafeFilename(config.getName()) + ".json");
                mapper.writeValue(resultFile, result);
            } catch (Exception e) {
                log.log(Level.SEVERE, "Unable to run config " + config.getName(), e);
            }
        }
    }

    private TestResult runConfig(TestConfig config) throws Exception {
        log.info("Running " + config.getName());

        TestResult result = new TestResult(config);

        Enumeration<String> dataEnum = config.getDataEnumeration();
        while (dataEnum.hasMoreElements()) {
            String testData = dataEnum.nextElement();
            Method method = getEncoderMethod(config.getEncoder());
            result.addResult(testData, (String) method.invoke(null, CharacterUtils.toNonPrintableString(testData)));
        }

        return result;
    }

    private Method getEncoderMethod(TestEncoder encoder)
            throws ClassNotFoundException, NoSuchMethodException, IOException {

        StringBuilder sb = new StringBuilder();
        sb.append(encoder.getLibrary());
        sb.append("-").append(encoder.getClassName());
        sb.append("-").append(encoder.getMethodName());
        String id = sb.toString();

        Method method = this.cachedMethods.get(id);
        if (method == null) {
            ClassLoader cl = getClassLoader(encoder.getLibrary());
            Class clazz = cl.loadClass(encoder.getClassName());
            method = clazz.getMethod(encoder.getMethodName(), String.class);
            this.cachedMethods.put(id, method);

            /*
            try {
                java.lang.reflect.Field f = ClassLoader.class.getDeclaredField("classes");
                f.setAccessible(true);
                //ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
                java.util.Vector<Class> classes = (java.util.Vector<Class>)f.get(cl);
                for (Class clazzA : classes) System.out.println(clazzA.getName());
            } catch (Exception e) {
                e.printStackTrace(System.err);
            }
            */
        }

        return method;
    }

    // expected format is comma separated list of groupId:artifactId:version
    private ClassLoader getClassLoader(String libsString) throws IOException {
        StringBuilder sb = new StringBuilder();
        StringTokenizer tokenizer = new StringTokenizer(libsString, ",");
        while (tokenizer.hasMoreElements()) {
            String libName = tokenizer.nextToken().trim();
            File jarFile = findOrDownloadLibrary(libName);
            if (jarFile != null) {
                if (sb.length() > 0)
                    sb.append(",");
                sb.append(jarFile.getAbsolutePath());
            } else {
                log.warning("Unable to find jar for " + libName);
            }
        }

        log.fine("Creating classloader with " + sb.toString());
        return ClassLoaderFactory.createClassLoader(sb.toString(),
                ConfigRunner.class.getClassLoader());
    }

    private File findOrDownloadLibrary(String name) throws IOException {
        String[] parts = name.split(":"); // groupId:artifactId:version
        String groupId = parts[0];
        String artifactId = parts[1];
        String version = parts[2];

        // first check local maven repository
        File localMavenRepoFile = new File(LOCAL_MAVEN_REPO,
                createPathFromLibraryParts(groupId, artifactId, version,
                        File.separator));
        if (localMavenRepoFile.exists())
            return localMavenRepoFile;

        // next check cache
        File localCacheFile = new File(LOCAL_CACHE, createPathFromLibraryParts(
                groupId, artifactId, version, File.separator));
        if (localCacheFile.exists())
            return localCacheFile;

        // finally, download to cache
        localCacheFile.getParentFile().mkdirs(); // create missing directory structure
        String url = REMOTE_MAVEN_REPO
                + createPathFromLibraryParts(groupId, artifactId, version, "/");
        log.fine("Downloading " +  url + " to " + localCacheFile.getAbsolutePath());
        FileUtils.downloadToFile(new URL(url), localCacheFile);

        return localCacheFile;
    }

    private String createPathFromLibraryParts(String groupId,
                                              String artifactId, String version, String separator) {

        StringBuilder sb = new StringBuilder();
        sb.append(FileUtils.getSafeFilename(groupId).replace(".", separator))
                .append(separator);
        sb.append(FileUtils.getSafeFilename(artifactId)).append(separator);
        sb.append(FileUtils.getSafeFilename(version)).append(separator);
        sb.append(FileUtils.getSafeFilename(artifactId)).append("-");
        sb.append(FileUtils.getSafeFilename(version)).append(".jar");
        return sb.toString();
    }

}
