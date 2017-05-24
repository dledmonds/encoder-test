package com.dledmonds.encodingtest.wrapper;

import java.io.IOException;
import java.io.StringWriter;

import org.apache.taglibs.standard.tag.common.core.OutSupport;

/**
 * Singleton wrapper to provide static method calls into JSTL c:out
 * 
 * @author dledmonds
 */
public class JstlCoutWrapper {

	public static String cOut(String data) {
		StringWriter writer = new StringWriter();
		try {
			OutSupport.out(new CoutDummyPageContext(writer), true, data);
		} catch (IOException ioe) {
			return "IOE Occurred";
		}
		return writer.toString();
	}

}
