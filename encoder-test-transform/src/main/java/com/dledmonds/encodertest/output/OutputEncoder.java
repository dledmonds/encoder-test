package com.dledmonds.encodertest.output;

/**
 * Output encoder interface as some output formats will require encoding to
 * ensure data displays as intended, i.e. Html output
 * 
 * @author dledmonds
 */
public interface OutputEncoder {
	public String encode(String text);
}
