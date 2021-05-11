package com.dledmonds.encodertest;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

/**
 * The result of running a TestConfig
 *
 * @author dledmonds
 */
public class TestResult {
	private TestConfig config;
	private Date generatedDate;
	private Map<String, String> properties = new TreeMap<>();
	private Map<String, String> results = new TreeMap<>();

	TestResult(TestConfig config) {
		this.config = config;
	}

	public String getName() {
		return config.getName();
	}

	public String getDescription() {
		return config.getDescription();
	}

	public String getDataFile() {
		return config.getDataFile();
	}

	public List<String> getTags() {
		return config.getTags();
	}

	public TestEncoder getEncoder() {
		return config.getEncoder();
	}

	public Date getGeneratedDate() {
		return generatedDate;
	}

	void setGeneratedDate(Date generatedDate) {
		this.generatedDate = generatedDate;
	}

	public Map<String, String> getProperties() {
		return properties;
	}

	void addProperty(String key, String value) {
		this.properties.put(key, value);
	}

	public Map<String, String> getResults() {
		return results;
	}

	void addResult(String originalText, String encodedText) {
		results.put(originalText, encodedText);
	}

}
