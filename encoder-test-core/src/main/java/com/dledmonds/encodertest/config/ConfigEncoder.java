package com.dledmonds.encodertest.config;

import com.fasterxml.jackson.annotation.JsonIgnore;

public class ConfigEncoder {
	private String id;
	private String library;
	private String className;
	private String methodName;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

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
