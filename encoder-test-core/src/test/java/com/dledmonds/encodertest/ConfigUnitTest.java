package com.dledmonds.encodertest;

import static org.junit.Assert.*;

import java.io.File;

import org.junit.Test;

import com.dledmonds.encodertest.config.ConfigEncoder;
import com.dledmonds.encodertest.config.ConfigTest;
import com.dledmonds.encodertest.config.ConfigTestList;
import com.fasterxml.jackson.databind.ObjectMapper;

public class ConfigUnitTest {

	public ConfigUnitTest() {
	}

	@Test
	public void test() throws Exception {
		ConfigTestList tests = new ConfigTestList();
		
		ConfigTest test = new ConfigTest();
		test.setName("Sample Test");
		test.addData("<");
		test.addData(">");
		test.addData("\n");
		ConfigEncoder encoder = new ConfigEncoder();
		encoder.setLibrary("encoder-1.2.1.jar");
		encoder.setClassName("org.owasp.encoder.Encoder");
		encoder.setMethodName("forHtml");
		test.addEncoder(encoder);
		tests.addTest(test);
		
		//ObjectMapper mapper = new ObjectMapper();
		
		// Convert object to JSON string and save into a file directly
		//mapper.writeValue(new File("config.json"), tests);
		
		// Convert object to JSON string
		//String jsonInString = mapper.writeValueAsString(tests);
		//System.out.println(jsonInString);

		// Convert object to JSON string and pretty print
		//jsonInString = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(tests);
		//System.out.println(jsonInString);
					
		//assertEquals("none", jsonInString);
	}

}
