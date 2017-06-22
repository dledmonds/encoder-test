package com.dledmonds.encodingtest.wrapper;

import org.owasp.esapi.ESAPI;
import org.owasp.esapi.errors.EncodingException;

/**
 * Singleton wrapper to provide static method calls into ESAPI encoding
 * 
 * @author dledmonds
 */
public class EsapiLegacyWrapper {

	private static EsapiLegacyWrapper ME;

	private org.owasp.esapi.Encoder encoder;

	private EsapiLegacyWrapper() {
		this.encoder = ESAPI.encoder();
	}

	private static EsapiLegacyWrapper getInstance() {
		if (ME == null) {
			ME = new EsapiLegacyWrapper();
		}
		return ME;
	}

	public static String encodeForHTML(String data) {
		return getInstance().encoder.encodeForHTML(data);
	}

	public static String encodeForHTMLAttribute(String data) {
		return getInstance().encoder.encodeForHTMLAttribute(data);
	}

	public static String encodeForJavaScript(String data) {
		return getInstance().encoder.encodeForJavaScript(data);
	}
	
	public static String encodeForCSS(String data) {
		return getInstance().encoder.encodeForCSS(data);
	}
	
	public static String encodeForURL(String data) throws EncodingException {
		return getInstance().encoder.encodeForURL(data);
	}
	
}
