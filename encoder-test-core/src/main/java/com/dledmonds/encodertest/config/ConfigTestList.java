package com.dledmonds.encodertest.config;

import java.util.ArrayList;
import java.util.List;

public class ConfigTestList {
	private List<ConfigTest> tests = new ArrayList<ConfigTest>();

	public List<ConfigTest> getTests() {
		return tests;
	}

	public void setTests(List<ConfigTest> tests) {
		this.tests = tests;
	}

	public void addTest(ConfigTest test) {
		this.tests.add(test);
	}

}
