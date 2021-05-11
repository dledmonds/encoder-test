package com.dledmonds.encodertest;

import java.util.ArrayList;
import java.util.List;

/**
 * A series of hardcoded TestConfigs
 *
 * @author dledmonds
 */
public class HardcodedTestConfigs {

    // constants for encoder types that will be used during the output phase
    public static final String ENCODE_CSS = "css-encoder";
    public static final String ENCODE_JAVASCRIPT = "javascript-encoder";
    public static final String ENCODE_HTML = "html-encoder";
    public static final String ENCODE_JAVA = "java-encoder";
    public static final String ENCODE_URI = "uri-encoder";
    public static final String ENCODE_XML = "xml-encoder";


    List<TestConfig> getAllTestConfigs() {
        List<TestConfig> configs = new ArrayList<>();
        configs.addAll(getApacheCommonsConfigs());
        configs.addAll(getJreConfigs());
        configs.addAll(getOwaspESAPIConfigs());
        configs.addAll(getOwaspJavaEncoderConfigs());
        return configs;
    }

    public List<TestConfig> getApacheCommonsConfigs() {
        List<TestConfig> configs = new ArrayList<>();

        for (String version : new String[]{"2.6", "2.5", "2.4", "2.3", "2.2", "2.1", "2.0"}) {
            for (String methodName : new String[]{"escapeHtml"}) {
                TestConfig tc = createTestConfig(
                        "apache-commons-lang-" + version + "-" + methodName,
                        "Apache Commons Lang " + version + " escape HTML tests for " + methodName,
                        "basic-html-encoding.txt",
                        new String[]{"java", ENCODE_HTML, "apache-commons-lang", "apache-commons-lang-" + version, methodName},
                        "commons-lang:commons-lang:" + version,
                        "org.apache.commons.lang.StringEscapeUtils",
                        methodName);
                configs.add(tc);
            }
        }

        for (String version : new String[]{"3.12.0", "3.11", "3.10", "3.9", "3.8.1", "3.8", "3.7", "3.6", "3.5", "3.4"}) {
            for (String methodName : new String[]{"escapeHtml3", "escapeHtml4"}) {
                TestConfig tc = createTestConfig(
                        "apache-commons-lang-" + version + "-" + methodName,
                        "Apache Commons Lang " + version + " escape HTML tests for " + methodName,
                        "basic-html-encoding.txt",
                        new String[]{"java", ENCODE_HTML, "apache-commons-lang", "apache-commons-lang-" + version, methodName},
                        "org.apache.commons:commons-lang3:" + version,
                        "org.apache.commons.lang3.StringEscapeUtils",
                        methodName);
                configs.add(tc);
            }
        }

        return configs;
    }

    public List<TestConfig> getJreConfigs() {
        List<TestConfig> configs = new ArrayList<>();

        for (String methodName : new String[]{"urlEncode"}) {
            TestConfig tc = createTestConfig(
                    "jre-net-urlencoder-" + methodName,
                    "JRE URI encoder tests for " + methodName,
                    "basic-uri-encoding.txt",
                    new String[]{"java", ENCODE_URI, "jre", methodName},
                    "com.dledmonds:encoder-test-java-wrapper:1-SNAPSHOT",
                    "com.dledmonds.encodertest.wrapper.JreWrapper",
                    methodName);
            configs.add(tc);
        }

        return configs;
    }

    // TODO - ESAPI still has some encoders not converted below
    public List<TestConfig> getOwaspESAPIConfigs() {
        List<TestConfig> configs = new ArrayList<>();

        // older releases
        for (String version : new String[]{"2.2.0.0", "2.1.0.1", "2.1.0", "2.0.1"}) {
            for (String methodName : new String[]{"encodeForHTML", "encodeForHTMLAttribute"}) {
                TestConfig tc = createTestConfig(
                        "owasp-esapi-legacy-" + version + "-" + methodName,
                        "OWASP ESAPI Legacy " + version + " HTML encoder tests for " + methodName,
                        "basic-html-encoding.txt",
                        new String[]{"java", ENCODE_HTML, "owasp-esapi-legacy", "owasp-esapi-legacy-" + version, methodName},
                        "org.owasp.esapi:esapi:" + version + ",log4j:log4j:1.2.17,com.dledmonds:encoder-test-java-wrapper-esapi-1-resources:1-SNAPSHOT,com.dledmonds:encoder-test-java-wrapper:1-SNAPSHOT",
                        "com.dledmonds.encodertest.wrapper.EsapiLegacyWrapper",
                        methodName);
                configs.add(tc);
            }
        }
        for (String version : new String[]{"2.2.0.0", "2.1.0.1", "2.1.0", "2.0.1"}) {
            for (String methodName : new String[]{"encodeForJavaScript"}) {
                TestConfig tc = createTestConfig(
                        "owasp-esapi-legacy-" + version + "-" + methodName,
                        "OWASP ESAPI Legacy " + version + " Javascript encoder tests for " + methodName,
                        "basic-javascript-encoding.txt",
                        new String[]{"java", ENCODE_JAVASCRIPT, "owasp-esapi-legacy", "owasp-esapi-legacy-" + version, methodName},
                        "org.owasp.esapi:esapi:" + version + ",log4j:log4j:1.2.17,com.dledmonds:encoder-test-java-wrapper-esapi-1-resources:1-SNAPSHOT,com.dledmonds:encoder-test-java-wrapper:1-SNAPSHOT",
                        "com.dledmonds.encodertest.wrapper.EsapiLegacyWrapper",
                        methodName);
                configs.add(tc);
            }
        }
        for (String version : new String[]{"2.2.0.0", "2.1.0.1", "2.1.0", "2.0.1"}) {
            for (String methodName : new String[]{"encodeForURL"}) {
                TestConfig tc = createTestConfig(
                        "owasp-esapi-legacy-" + version + "-" + methodName,
                        "OWASP ESAPI Legacy " + version + " URL encoder tests for " + methodName,
                        "basic-uri-encoding.txt",
                        new String[]{"java", ENCODE_URI, "owasp-esapi-legacy", "owasp-esapi-legacy-" + version, methodName},
                        "org.owasp.esapi:esapi:" + version + ",log4j:log4j:1.2.17,com.dledmonds:encoder-test-java-wrapper-esapi-1-resources:1-SNAPSHOT,com.dledmonds:encoder-test-java-wrapper:1-SNAPSHOT",
                        "com.dledmonds.encodertest.wrapper.EsapiLegacyWrapper",
                        methodName);
                configs.add(tc);
            }
        }
        for (String version : new String[]{"2.2.0.0", "2.1.0.1", "2.1.0", "2.0.1"}) {
            for (String methodName : new String[]{"encodeForXML", "encodeForXMLAttribute"}) {
                TestConfig tc = createTestConfig(
                        "owasp-esapi-legacy-" + version + "-" + methodName,
                        "OWASP ESAPI Legacy " + version + " XML encoder tests for " + methodName,
                        "basic-xml-encoding.txt",
                        new String[]{"java", ENCODE_XML, "owasp-esapi-legacy", "owasp-esapi-legacy-" + version, methodName},
                        "org.owasp.esapi:esapi:" + version + ",log4j:log4j:1.2.17,com.dledmonds:encoder-test-java-wrapper-esapi-1-resources:1-SNAPSHOT,com.dledmonds:encoder-test-java-wrapper:1-SNAPSHOT",
                        "com.dledmonds.encodertest.wrapper.EsapiLegacyWrapper",
                        methodName);
                configs.add(tc);
            }
        }

        // newer releases
        for (String version : new String[]{"2.2.3.1", "2.2.3.0", "2.2.2.0", "2.2.1.1", "2.2.1.0"}) {
            for (String methodName : new String[]{"encodeForHTML", "encodeForHTMLAttribute"}) {
                TestConfig tc = createTestConfig(
                        "owasp-esapi-legacy-" + version + "-" + methodName,
                        "OWASP ESAPI Legacy " + version + " HTML encoder tests for " + methodName,
                        "basic-html-encoding.txt",
                        new String[]{"java", ENCODE_HTML, "owasp-esapi-legacy", "owasp-esapi-legacy-" + version, methodName},
                        "org.owasp.esapi:esapi:" + version + ",log4j:log4j:1.2.17,com.dledmonds:encoder-test-java-wrapper-esapi-2-resources:1-SNAPSHOT,com.dledmonds:encoder-test-java-wrapper:1-SNAPSHOT",
                        "com.dledmonds.encodertest.wrapper.EsapiLegacyWrapper",
                        methodName);
                configs.add(tc);
            }
        }
        for (String version : new String[]{"2.2.3.1", "2.2.3.0", "2.2.2.0", "2.2.1.1", "2.2.1.0"}) {
            for (String methodName : new String[]{"encodeForJavaScript"}) {
                TestConfig tc = createTestConfig(
                        "owasp-esapi-legacy-" + version + "-" + methodName,
                        "OWASP ESAPI Legacy " + version + " Javascript encoder tests for " + methodName,
                        "basic-javascript-encoding.txt",
                        new String[]{"java", ENCODE_JAVASCRIPT, "owasp-esapi-legacy", "owasp-esapi-legacy-" + version, methodName},
                        "org.owasp.esapi:esapi:" + version + ",log4j:log4j:1.2.17,com.dledmonds:encoder-test-java-wrapper-esapi-2-resources:1-SNAPSHOT,com.dledmonds:encoder-test-java-wrapper:1-SNAPSHOT",
                        "com.dledmonds.encodertest.wrapper.EsapiLegacyWrapper",
                        methodName);
                configs.add(tc);
            }
        }
        for (String version : new String[]{"2.2.3.1", "2.2.3.0", "2.2.2.0", "2.2.1.1", "2.2.1.0"}) {
            for (String methodName : new String[]{"encodeForURL"}) {
                TestConfig tc = createTestConfig(
                        "owasp-esapi-legacy-" + version + "-" + methodName,
                        "OWASP ESAPI Legacy " + version + " URL encoder tests for " + methodName,
                        "basic-uri-encoding.txt",
                        new String[]{"java", ENCODE_URI, "owasp-esapi-legacy", "owasp-esapi-legacy-" + version, methodName},
                        "org.owasp.esapi:esapi:" + version + ",log4j:log4j:1.2.17,com.dledmonds:encoder-test-java-wrapper-esapi-2-resources:1-SNAPSHOT,com.dledmonds:encoder-test-java-wrapper:1-SNAPSHOT",
                        "com.dledmonds.encodertest.wrapper.EsapiLegacyWrapper",
                        methodName);
                configs.add(tc);
            }
        }
        for (String version : new String[]{"2.2.3.1", "2.2.3.0", "2.2.2.0", "2.2.1.1", "2.2.1.0"}) {
            for (String methodName : new String[]{"encodeForXML", "encodeForXMLAttribute"}) {
                TestConfig tc = createTestConfig(
                        "owasp-esapi-legacy-" + version + "-" + methodName,
                        "OWASP ESAPI Legacy " + version + " XML encoder tests for " + methodName,
                        "basic-xml-encoding.txt",
                        new String[]{"java", ENCODE_XML, "owasp-esapi-legacy", "owasp-esapi-legacy-" + version, methodName},
                        "org.owasp.esapi:esapi:" + version + ",log4j:log4j:1.2.17,com.dledmonds:encoder-test-java-wrapper-esapi-2-resources:1-SNAPSHOT,com.dledmonds:encoder-test-java-wrapper:1-SNAPSHOT",
                        "com.dledmonds.encodertest.wrapper.EsapiLegacyWrapper",
                        methodName);
                configs.add(tc);
            }
        }

        return configs;
    }

    public List<TestConfig> getOwaspJavaEncoderConfigs() {
        List<TestConfig> configs = new ArrayList<>();
        for (String version : new String[]{"1.2.3", "1.2.2", "1.2.1", "1.2", "1.1.1", "1.1"}) {
            for (String methodName : new String[]{"forHtml", "forHtmlContent", "forHtmlAttribute", "forHtmlUnquotedAttribute"}) {
                TestConfig tc = createTestConfig(
                        "owasp-java-encoder-" + version + "-" + methodName,
                        "OWASP java-encoder " + version + " HTML encoder tests for " + methodName,
                        "basic-html-encoding.txt",
                        new String[]{"java", ENCODE_HTML, "owasp-java-encoder", "owasp-java-encoder-" + version, methodName},
                        "org.owasp.encoder:encoder:" + version,
                        "org.owasp.encoder.Encode",
                        methodName);
                configs.add(tc);
            }
            for (String methodName : new String[]{"forCssString", "forCssUrl"}) {
                TestConfig tc = createTestConfig(
                        "owasp-java-encoder-" + version + "-" + methodName,
                        "OWASP java-encoder " + version + " CSS encoder tests for " + methodName,
                        "basic-css-encoding.txt",
                        new String[]{"java", ENCODE_CSS, "owasp-java-encoder", "owasp-java-encoder-" + version, methodName},
                        "org.owasp.encoder:encoder:" + version,
                        "org.owasp.encoder.Encode",
                        methodName);
                configs.add(tc);
            }
            for (String methodName : new String[]{"forUri", "forUriComponent"}) {
                TestConfig tc = createTestConfig(
                        "owasp-java-encoder-" + version + "-" + methodName,
                        "OWASP java-encoder " + version + " URI encoder tests for " + methodName,
                        "basic-uri-encoding.txt",
                        new String[]{"java", ENCODE_URI, "owasp-java-encoder", "owasp-java-encoder-" + version, methodName},
                        "org.owasp.encoder:encoder:" + version,
                        "org.owasp.encoder.Encode",
                        methodName);
                configs.add(tc);
            }
            for (String methodName : new String[]{"forXml", "forXmlContent", "forXmlAttribute", "forXmlComment", "forCDATA"}) {
                TestConfig tc = createTestConfig(
                        "owasp-java-encoder-" + version + "-" + methodName,
                        "OWASP java-encoder " + version + " XML encoder tests for " + methodName,
                        "basic-xml-encoding.txt",
                        new String[]{"java", ENCODE_XML, "owasp-java-encoder", "owasp-java-encoder-" + version, methodName},
                        "org.owasp.encoder:encoder:" + version,
                        "org.owasp.encoder.Encode",
                        methodName);
                configs.add(tc);
            }
            for (String methodName : new String[]{"forJava"}) {
                TestConfig tc = createTestConfig(
                        "owasp-java-encoder-" + version + "-" + methodName,
                        "OWASP java-encoder " + version + " java encoder tests for " + methodName,
                        "basic-java-encoding.txt",
                        new String[]{"java", ENCODE_JAVA, "owasp-java-encoder", "owasp-java-encoder-" + version, methodName},
                        "org.owasp.encoder:encoder:" + version,
                        "org.owasp.encoder.Encode",
                        methodName);
                configs.add(tc);
            }
            for (String methodName : new String[]{"forJavaScript", "forJavaScriptAttribute", "forJavaScriptBlock", "forJavaScriptSource"}) {
                TestConfig tc = createTestConfig(
                        "owasp-java-encoder-" + version + "-" + methodName,
                        "OWASP java-encoder " + version + " Javascript encoder tests for " + methodName,
                        "basic-javascript-encoding.txt",
                        new String[]{"java", ENCODE_JAVASCRIPT, "owasp-java-encoder", "owasp-java-encoder-" + version, methodName},
                        "org.owasp.encoder:encoder:" + version,
                        "org.owasp.encoder.Encode",
                        methodName);
                configs.add(tc);
            }
        }
        return configs;
    }

    private TestConfig createTestConfig(String name, String description, String dataFile, String[] tags,
            String encoderLibrary, String encoderClassName, String encoderMethod) {

        TestEncoder te = new TestEncoder();
        te.setLibrary(encoderLibrary);
        te.setClassName(encoderClassName);
        te.setMethodName(encoderMethod);

        TestConfig tc = new TestConfig();
        tc.setName(name);
        tc.setDescription(description);
        tc.setDataFile(dataFile);
        tc.setEncoder(te);
        for (String tag : tags) tc.addTag(tag);

        return tc;
    }

}
