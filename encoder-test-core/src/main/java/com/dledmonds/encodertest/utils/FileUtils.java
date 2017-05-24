package com.dledmonds.encodertest.utils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.channels.Channels;
import java.nio.channels.ReadableByteChannel;

public class FileUtils {

	public static String getSafeFilename(String filename) {
		return filename.replaceAll("[^a-zA-Z0-9.-]", "_");
	}

	/**
	 * Download URL to give File
	 */
	public static void downloadToFile(URL link, File out)
			throws MalformedURLException, IOException {
		ReadableByteChannel rbc = Channels.newChannel(link.openStream());
		try (FileOutputStream fos = new FileOutputStream(out)) {
			fos.getChannel().transferFrom(rbc, 0, Long.MAX_VALUE);
			fos.close();
		}
	}

}
