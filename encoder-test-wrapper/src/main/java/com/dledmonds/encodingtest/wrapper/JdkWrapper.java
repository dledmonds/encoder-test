package com.dledmonds.encodingtest.wrapper;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

/**
 * Singleton wrapper to provide static method calls into java encoding of non
 * static of multiple argument methods
 * 
 * @author dledmonds
 */
public class JdkWrapper {

	public static String urlEncode(String data) throws UnsupportedEncodingException {
		return URLEncoder.encode(data, "UTF-8");
	}

}
