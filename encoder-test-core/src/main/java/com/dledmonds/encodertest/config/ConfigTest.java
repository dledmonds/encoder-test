package com.dledmonds.encodertest.config;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Enumeration;
import java.util.List;

import com.dledmonds.encodertest.utils.FileUtils;
import com.fasterxml.jackson.annotation.JsonIgnore;

public class ConfigTest {
	private String name;
	private String description;
	private String outputType = "";
	private String dataFile;
	private List<String> data = new ArrayList<String>();
	private List<ConfigEncoder> encoders = new ArrayList<ConfigEncoder>();

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

	public String getOutputType() {
		return outputType;
	}

	public void setOutputType(String outputType) {
		this.outputType = outputType;
	}

	public String getDataFile() {
		return dataFile;
	}

	public void setDataFile(String dataFile) {
		this.dataFile = dataFile;
	}

	public List<String> getData() {
		return data;
	}

	public void setData(List<String> data) {
		this.data = data;
	}

	public void addData(String text) {
		this.data.add(text);
	}

	public List<ConfigEncoder> getEncoders() {
		return encoders;
	}

	public void setEncoders(List<ConfigEncoder> encoders) {
		this.encoders = encoders;
	}

	public void addEncoder(ConfigEncoder encoder) {
		this.encoders.add(encoder);
	}

	@JsonIgnore
	public Enumeration<String> getDataEnumeration() {
		if (getDataFile() == null)
			return Collections.enumeration(data);

		return new FileEnumeration(getClass().getResourceAsStream(
				"/encodingData/" + FileUtils.getSafeFilename(getDataFile())));
	}

	// create an Enumeration from a file
	class FileEnumeration implements Enumeration<String> {
		private BufferedReader reader;
		private String nextToken;

		private FileEnumeration(InputStream in) {
			if (in != null)
				reader = new BufferedReader(new InputStreamReader(in));
		}

		@Override
		public boolean hasMoreElements() {
			if (reader == null)
				return false;
			try {
				if (!reader.ready())
					return false;
				nextToken = reader.readLine();
				if (nextToken == null) {
					reader.close();
					return false;
				}
				return true;
			} catch (IOException ioe) {
				// can't do much about this here
				return false;
			}
		}

		@Override
		public String nextElement() {
			return nextToken;
		}

	}
}
