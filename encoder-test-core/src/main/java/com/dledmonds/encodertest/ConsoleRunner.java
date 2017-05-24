package com.dledmonds.encodertest;

import java.io.File;

import com.dledmonds.encodertest.config.ConfigTestList;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * Main entry point into application that allows basic command line
 * configuration
 * 
 * @author dledmonds
 */
// http://www.fileformat.info/info/charset/UTF-8/list.htm
public class ConsoleRunner {

	public static void main(String[] args) {
		// assume we have an output directory in current directory, but don't
		// attempt to create it, just fail if it doesn't exist
		File outputDir = new File(".", "output");
		if (!outputDir.exists()) {
			System.err.println(outputDir.getAbsolutePath() + " does not exist");
			System.exit(1);
		}

		ConfigRunner cr = new ConfigRunner(outputDir);

		ConfigTestList tests = null;
		try {
			if (args.length > 0) {
				ObjectMapper mapper = new ObjectMapper();
				for (int i = 0; i < args.length; i++) {
					tests = mapper.readValue(new File(args[i]),
							ConfigTestList.class);
					cr.run(tests);
				}
			} else {
				System.err.println("No config file specified");
				System.exit(1);
			}
		} catch (Exception e) {
			e.printStackTrace(System.err);
		}

		// Quick check to see which classes were loaded by given ClassLoader
		/*
		 * try { java.lang.reflect.Field f =
		 * ClassLoader.class.getDeclaredField("classes"); f.setAccessible(true);
		 * ClassLoader classLoader =
		 * Thread.currentThread().getContextClassLoader();
		 * java.util.Vector<Class> classes =
		 * (java.util.Vector<Class>)f.get(classLoader); for (Class clazz :
		 * classes) System.out.println(clazz.getName()); } catch (Exception e) {
		 * e.printStackTrace(System.err); }
		 */
	}

}
