package com.dledmonds.encodertest;

import com.fasterxml.jackson.annotation.JsonIgnore;

/**
 * The encoding library to test (might be a wrapper)
 *
 * @author dledmonds
 */
public class TestEncoder {
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

	@JsonIgnore
	public String getDescription() {
		return getLibrary() + "@" + getClassName() + "@" + getMethodName();
	}
}
