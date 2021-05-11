package com.dledmonds.encodertest.utils;

public class CharacterUtils {

	public static final char[] CHARACTERS = { '\n', '\r' };
	public static final String[] CHARACTER_REPLACEMENTS = { "{CR}", "{LF}" };
	public static final String[] CHARACTER_DESCRIPTIONS = { "Carriage return",
			"Line feed" };

	/**
	 * Returns true if text contain non printable characters
	 */
	public static boolean containsNotPrintableCharacters(String text) {
		char c = 0;
		for (int i = 0; i < text.length(); i++) {
			c = text.charAt(i);
			for (int j = 0; j < CHARACTERS.length; j++) {
				if (CHARACTERS[j] == c)
					return true;
			}
		}

		return false;
	}

	/**
	 * Converts any non printable characters in text to return a printable
	 * version with substitutions
	 */
	public static String toPrintableString(String text) {
		if (!containsNotPrintableCharacters(text)) return text;
		
		StringBuilder sb = new StringBuilder();
		char c = 0;
		boolean foundMatch = false;
		for (int i = 0; i < text.length(); i++) {
			c = text.charAt(i);
			foundMatch = false;
			for (int j = 0; j < CHARACTERS.length; j++) {
				if (CHARACTERS[j] == c) {
					sb.append(CHARACTER_REPLACEMENTS[j]);
					foundMatch = true;
					break;
				}
			}
			if (!foundMatch)
				sb.append(c);
		}
		return sb.toString();
	}

	/**
	 * Converts any non printable character substitutions in text to return a
	 * non printable version without substitutions
	 */
	public static String toNonPrintableString(String text) {
		// inefficient, but performance isn't really a concern
		String finalText = text;
		for (int i = 0; i < CHARACTERS.length; i++) {
			if (text.contains(CHARACTER_REPLACEMENTS[i])) {
				finalText = finalText.replace(CHARACTER_REPLACEMENTS[i],
						Character.toString(CHARACTERS[i]));
			}
		}
		return finalText;
	}

}
