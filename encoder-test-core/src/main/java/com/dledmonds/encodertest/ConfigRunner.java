package com.dledmonds.encodertest;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.lang.reflect.Method;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;
import java.util.StringTokenizer;

import com.dledmonds.encodertest.config.ConfigEncoder;
import com.dledmonds.encodertest.config.ConfigOutputType;
import com.dledmonds.encodertest.config.ConfigTest;
import com.dledmonds.encodertest.config.ConfigTestList;
import com.dledmonds.encodertest.output.DefaultHtmlWriter;
import com.dledmonds.encodertest.output.HighlightBaselineDifferenceHtmlWriter;
import com.dledmonds.encodertest.output.HighlightLowestFrequencyHtmlWriter;
import com.dledmonds.encodertest.output.HtmlWriter;
import com.dledmonds.encodertest.utils.CharacterUtils;
import com.dledmonds.encodertest.utils.ClassLoaderFactory;
import com.dledmonds.encodertest.utils.FileUtils;

/**
 * Runs a configuration test
 * 
 * @author dledmonds
 */
public class ConfigRunner {

	// local maven repository, assumed to be ~/.m2/repository
	private static File LOCAL_MAVEN_REPO = new File(
			System.getProperty("user.home"), ".m2" + File.separator
					+ "repository");

	// local cache for libraries not already in local maven repository
	private static File LOCAL_CACHE = new File(System.getProperty("user.home"),
			".encoder-test");

	// remote maven repository to download required libraries from
	private static String REMOTE_MAVEN_REPO = "http://central.maven.org/maven2/";

	private File baseDir;

	// cache these to speed things up, reflection is slow
	private Map<String, Method> cachedMethods;

	ConfigRunner(File baseDir) {
		this.baseDir = baseDir;
		this.cachedMethods = new HashMap<String, Method>();
	}

	void run(ConfigTestList tests) {
		for (ConfigTest test : tests.getTests()) {
			runTest(test);
		}
	}

	private void runTest(ConfigTest test) {
		File outFile = new File(this.baseDir, FileUtils.getSafeFilename(test
				.getName()) + ".html");
		try (Writer writer = new FileWriter(outFile)) {
			if (test.getOutputType().equals(ConfigOutputType.HTML.toString())) {
				saveTest(test, new DefaultHtmlWriter(writer));
			} else if (test.getOutputType().equals(
					ConfigOutputType.HTML_BASELINE_DIFF.toString())) {
				saveTest(test,
						new HighlightBaselineDifferenceHtmlWriter(writer));
			} else if (test.getOutputType().equals(
					ConfigOutputType.HTML_ENCODED_DIFF.toString())) {
				saveTest(test, new HighlightLowestFrequencyHtmlWriter(writer));
			} else {
				throw new RuntimeException("No output type set");
			}
		} catch (Exception e) {
			e.printStackTrace(System.err);
		}
	}

	private void saveTest(ConfigTest test, HtmlWriter writer) throws Exception {
		writer.startHtml();
		writer.startHead(test.getName());
		writer.startBody(test.getName(), test.getDescription() == null ? ""
				: test.getDescription());
		writer.startTable();

		writer.startTableRow();
		writer.addTableRowHeader("Value", null);
		for (ConfigEncoder encoder : test.getEncoders()) {
			writer.addTableRowHeader(encoder.getId(), encoder.getDescription());
		}
		writer.endTableRow();

		Enumeration<String> dataEnum = test.getDataEnumeration();
		while (dataEnum.hasMoreElements()) {
			String testData = dataEnum.nextElement();
			writer.startTableRow();
			writer.addTableRowBaselineData(CharacterUtils
					.toNonPrintableString(testData));

			for (ConfigEncoder encoder : test.getEncoders()) {
				Method method = getEncoderMethod(encoder);
				writer.addTableRowData((String) method.invoke(null,
						CharacterUtils.toNonPrintableString(testData)));
			}

			writer.endTableRow();
		}
		writer.endTable();

		// TODO: add key from CharacterUtils using description constants

		writer.endBody();
		writer.endHtml();
	}

	private Method getEncoderMethod(ConfigEncoder encoder)
			throws ClassNotFoundException, NoSuchMethodException, IOException,
			MalformedURLException {

		String id = encoder.getId();

		Method method = this.cachedMethods.get(id);
		if (method == null) {
			ClassLoader cl = getClassLoader(encoder.getLibrary());
			Class clazz = cl.loadClass(encoder.getClassName());
			method = clazz.getMethod(encoder.getMethodName(), String.class);
			this.cachedMethods.put(id, method);
		}

		return method;
	}

	// expected format is comma separated list of groupId:artifactId:version
	private ClassLoader getClassLoader(String libsString)
			throws MalformedURLException, IOException {
		StringBuilder sb = new StringBuilder();
		StringTokenizer tokenizer = new StringTokenizer(libsString, ",");
		while (tokenizer.hasMoreElements()) {
			String libName = tokenizer.nextToken().trim();
			// System.out.println("Looking for " + libName);
			File jarFile = findOrDownloadLibrary(libName);
			if (jarFile != null) {
				// System.out.println("Found " + jarFile.getAbsolutePath());
				if (sb.length() > 0)
					sb.append(",");
				sb.append(jarFile.getAbsolutePath());
			}
		}
		// System.out.println("Passing to classloader : " + sb.toString());

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
		localCacheFile.getParentFile().mkdirs(); // create missing directory
													// structure
		String url = REMOTE_MAVEN_REPO
				+ createPathFromLibraryParts(groupId, artifactId, version, "/");
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
