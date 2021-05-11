package com.dledmonds.encodertest.output;

public class HtmlOutputEncoder implements OutputEncoder {

	public String encode(String text) {
		// deliberately not using a standard html encoder as it's likely we'll
		// be
		// testing them and don't want them as a dependency
		return text.replace("&", "&amp;").replace("<", "&lt;")
				.replace(">", "&gt;");
	}

}
