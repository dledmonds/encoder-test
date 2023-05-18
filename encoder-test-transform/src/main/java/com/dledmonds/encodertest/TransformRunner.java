package com.dledmonds.encodertest;

import com.dledmonds.encodertest.output.HighlightBaselineDifferenceHtmlWriter;
import com.dledmonds.encodertest.output.HtmlWriter;
import com.dledmonds.encodertest.utils.CharacterUtils;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.io.FileWriter;
import java.io.FilenameFilter;
import java.io.IOException;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;

/**
 * Transforms the JSON output from the encoder tests into a more friendly format, like HTML
 *
 * @author dledmonds
 */
public class TransformRunner {

    private final Logger log = Logger.getLogger(this.getClass().getName());

    private File inputDir;
    private File outputDir;

    TransformRunner(File inputDir, File outputDir) {
        this.inputDir = inputDir;
        this.outputDir = outputDir;
    }

    public void run() {
        ObjectMapper mapper = new ObjectMapper();
        List<TestResult> results = new ArrayList<>();

        try {
            File[] jsonFiles = inputDir.listFiles(new FilenameFilter() {
                @Override
                public boolean accept(File dir, String name) {
                    return name.toLowerCase().endsWith(".json");
                }
            });
            for (File jsonFile : jsonFiles) {
                TestResult tr = mapper.readValue(jsonFile, TestResult.class);
                results.add(tr);
            }
        } catch (Exception e) {
            log.log(Level.SEVERE, "Unable to read results", e);
        }

        try {
            transformEncoders(results);
        } catch (Exception e) {
            log.log(Level.SEVERE, "Unable to transform", e);
        }
    }

    void transformEncoders(List<TestResult> results) throws Exception {
        // all languages
        transformEncodersByTag(results, "html-encoder", "All_HTML_Encoder_Test.html",
                "All HTML Encoders", "Comparison of most popular HTML encoders");

        transformEncodersByTag(results, "xml-encoder", "All_XML_Encoder_Test.html",
                "All XML Encoders", "Comparison of most popular XML encoders");

        transformEncodersByTag(results, "css-encoder", "All_CSS_Encoder_Test.html",
                "All CSS Encoders", "Comparison of most popular CSS encoders");

        transformEncodersByTag(results, "javascript-encoder", "All_Javascript_Encoder_Test.html",
                "All Javascript Encoders", "Comparison of most popular Javascript encoders");

        transformEncodersByTag(results, "uri-encoder", "All_URL_Encoder_Test.html",
                "All URL Encoders", "Comparison of most popular URL encoders");

        // java only
        transformEncodersByTag(results, "java", "All_Java_Encoder_Test.html",
                "All Java Encoders", "Comparison of most popular Java encoders");

        transformEncodersByTag(results, "java-encoder", "All_Java_String_Encoder_Test.html",
                "All Java String Encoders", "Comparison of most popular Java String encoders");

        transformEncodersByTags(results, Arrays.asList("java", "html-encoder"), "All_Java_HTML_Encoder_Test.html",
                "All Java HTML Encoders", "Comparison of most popular Java HTML encoders");

        transformEncodersByTags(results, Arrays.asList("java", "xml-encoder"), "All_Java_XML_Encoder_Test.html",
                "All Java XML Encoders", "Comparison of most popular Java XML encoders");

        transformEncodersByTags(results, Arrays.asList("java", "css-encoder"), "All_Java_CSS_Encoder_Test.html",
                "All Java CSS Encoders", "Comparison of most popular Java CSS encoders");

        transformEncodersByTags(results, Arrays.asList("java", "javascript-encoder"), "All_Java_Javascript_Encoder_Test.html",
                "All Java Javascript Encoders", "Comparison of most popular Java Javascript encoders");

        transformEncodersByTags(results, Arrays.asList("java", "uri-encoder"), "All_Java_URL_Encoder_Test.html",
                "All Java URL Encoders", "Comparison of most popular Java URL encoders");


        // dotnet only
        transformEncodersByTag(results, "dotnet", "All_dotNET_Encoder_Test.html",
                "All .NET Encoders", "Comparison of most popular .NET encoders");
    }

    void transformEncodersByTag(List<TestResult> results, String tag, String fileName, String title, String description) throws Exception {
        transformEncodersByTags(results, Arrays.asList(tag), fileName, title, description);
    }

    void transformEncodersByTags(List<TestResult> results, List<String> tags, String fileName, String title, String description) throws Exception {
        Map<String, TestResult> filteredEncoders = new HashMap<>();
        for (TestResult result : results) {
            if (result.getTags().containsAll(tags)) {
                String key = result.getEncoder().getClassName() + "." + result.getEncoder().getMethodName();
                TestResult tmp = filteredEncoders.get(key);
                if (tmp != null) {
                    // check if this is a later version of the encoder
                    if (getVersion(result.getEncoder()) > getVersion(tmp.getEncoder())) {
                        filteredEncoders.put(key, result);
                    }
                } else {
                    filteredEncoders.put(key, result);
                }
            }
        }

        List<TestResult> resultList = new ArrayList<>(filteredEncoders.values());
        resultList = resultList.stream().sorted(Comparator.comparing(TestResult::getName)).collect(Collectors.toList());
        writeToHtml(resultList, new File(outputDir, fileName), title, description);
    }

    private void writeToHtml(List<TestResult> results, File outputFile, String title, String description) throws IOException {
        try (FileWriter out = new FileWriter(outputFile)) {
            HtmlWriter writer = new HighlightBaselineDifferenceHtmlWriter(out);
            writer.startHtml();
            writer.startHead(title);
            writer.startBody(title, description);
            writer.startTable();

            // table header
            writer.startTableRow();
            writer.addTableRowHeader("Value", null);
            for (TestResult result : results) {
                writer.addTableRowHeader(result.getName(), result.getDescription());
            }
            writer.endTableRow();

            // table body
            if (!results.isEmpty()) {
                for (String key : results.get(0).getResults().keySet()) {
                    writer.startTableRow();
                    writer.addTableRowBaselineData(CharacterUtils.toNonPrintableString(key));
                    for (TestResult result : results) {
                        writer.addTableRowData(CharacterUtils.toNonPrintableString(result.getResults().get(key)));
                    }
                    writer.endTableRow();
                }
            }

            writer.endTable();
            writer.endBody();
            writer.endHtml();
        }
    }

    /**
     * Get a numeric representation of the encoder version
     * @param encoder
     */
    // TODO unit test this code
    long getVersion(TestResult.TestEncoder encoder) {
        long version = 0l;
        String[] parts = encoder.getLibrary().split(",");
        if (parts.length >= 1) {
            String mainLibrary = parts[0];
            parts = mainLibrary.split(":");
            if (parts.length >= 3) {
                String versionStr = parts[parts.length-1];
                parts = versionStr.split("\\.");
                if (parts.length >= 1) {
                    try {
                        for (int i=0; i<parts.length; i++) {
                            version += (Long.parseLong(parts[i]) * (Math.pow(10, 10-i)));
                        }
                    } catch (NumberFormatException nfe) {
                        log.warning("Unable to parse " + versionStr + " to numeric parts");
                    }
                }
            }
        }
        return version;
    }

}
