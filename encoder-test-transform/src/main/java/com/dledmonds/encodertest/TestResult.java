package com.dledmonds.encodertest;

import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

/**
 * The result of running a TestConfig
 *
 * @author dledmonds
 */
@ToString
@EqualsAndHashCode
public class TestResult {
	private String name;
	private String description;
	private String dataFile;
	private List<String> tags;
	private Date generatedDate;
	private TestEncoder encoder;
	private Map<String, String> properties = new TreeMap<>();
	private Map<String, String> results = new TreeMap<>();

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getDataFile() {
		return dataFile;
	}

	public void setDataFile(String dataFile) {
		this.dataFile = dataFile;
	}

	public List<String> getTags() {
		return tags;
	}

	public void setTags(List<String> tags) {
		this.tags = tags;
	}

	public Date getGeneratedDate() {
		return generatedDate;
	}

	void setGeneratedDate(Date generatedDate) {
		this.generatedDate = generatedDate;
	}

	public TestEncoder getEncoder() {
		return encoder;
	}

	public void setEncoder(TestEncoder encoder) {
		this.encoder = encoder;
	}

	public Map<String, String> getProperties() {
		return properties;
	}

	public void setProperties(Map<String, String> properties) {
		this.properties = properties;
	}

	public Map<String, String> getResults() {
		return results;
	}

	public void setResults(Map<String, String> results) {
		this.results = results;
	}


	static class TestEncoder {
		private String library;
		private String className;
		private String methodName;

		public String getLibrary() {
			return library;
		}

		public String getClassName() {
			return className;
		}

		public String getMethodName() {
			return methodName;
		}

		public void setLibrary(String library) {
			this.library = library;
		}

		public void setClassName(String className) {
			this.className = className;
		}

		public void setMethodName(String methodName) {
			this.methodName = methodName;
		}
	}

}
