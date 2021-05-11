package com.dledmonds.encodertest;

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

/**
 * Combination of the encoder to test and the dataset to test it against
 *
 * @author dledmonds
 */
public class TestConfig {
	private String name;
	private String description;
	private String dataFile;
	private List<String> tags = new ArrayList<>();
	private TestEncoder encoder;

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

	public void addTag(String tag) {
		tags.add(tag);
	}

	public void addTags(String ... tags) {
		for (String tag : tags) {
			this.tags.add(tag);
		}
	}

	public TestEncoder getEncoder() {
		return encoder;
	}

	public void setEncoder(TestEncoder encoder) {
		this.encoder = encoder;
	}

	@JsonIgnore
	public Enumeration<String> getDataEnumeration() {
		if (getDataFile() == null)
			return Collections.enumeration(new ArrayList<>());

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
