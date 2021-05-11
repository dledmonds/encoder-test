package com.dledmonds.encodertest.utils;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.net.URLClassLoader;
import java.security.AccessController;
import java.security.PrivilegedAction;
import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Locale;
import java.util.Set;
import java.util.StringTokenizer;

/**
 * Code originally taken from Apache Catalina codebase with a few modifications
 * 
 * @author dledmonds
 */
public final class ClassLoaderFactory {

	/**
	 * Create and return a new class loader, based on the configuration defaults
	 * and the specified directory paths:
	 *
	 * @param repositories
	 *            List of class directories, jar files, jar directories or URLS
	 *            that should be added to the repositories of the class loader.
	 * @param parent
	 *            Parent class loader for the new class loader, or
	 *            <code>null</code> for the system class loader.
	 */
	public static ClassLoader createClassLoader(List<Repository> repositories,
			final ClassLoader parent) throws IOException {

		// Construct the "class path" for this class loader
		Set<URL> set = new LinkedHashSet<URL>();

		if (repositories != null) {
			for (Repository repository : repositories) {
				if (repository.getType() == RepositoryType.URL) {
					URL url = new URL(repository.getLocation());
					set.add(url);
				} else if (repository.getType() == RepositoryType.DIR) {
					File directory = new File(repository.getLocation());
					directory = directory.getCanonicalFile();
					if (!validateFile(directory, RepositoryType.DIR)) {
						continue;
					}
					URL url = directory.toURI().toURL();
					set.add(url);
				} else if (repository.getType() == RepositoryType.JAR) {
					File file = new File(repository.getLocation());
					file = file.getCanonicalFile();
					if (!validateFile(file, RepositoryType.JAR)) {
						continue;
					}
					URL url = file.toURI().toURL();
					set.add(url);
				} else if (repository.getType() == RepositoryType.GLOB) {
					File directory = new File(repository.getLocation());
					directory = directory.getCanonicalFile();
					if (!validateFile(directory, RepositoryType.GLOB)) {
						continue;
					}

					String filenames[] = directory.list();
					for (int j = 0; j < filenames.length; j++) {
						String filename = filenames[j]
								.toLowerCase(Locale.ENGLISH);
						if (!filename.endsWith(".jar"))
							continue;
						File file = new File(directory, filenames[j]);
						file = file.getCanonicalFile();
						if (!validateFile(file, RepositoryType.JAR)) {
							continue;
						}

						URL url = file.toURI().toURL();
						set.add(url);
					}
				}
			}
		}

		// Construct the class loader itself
		final URL[] array = set.toArray(new URL[set.size()]);

		return AccessController
				.doPrivileged(new PrivilegedAction<URLClassLoader>() {
					@Override
					public URLClassLoader run() {
						if (parent == null)
							return new URLClassLoader(array);
						else
							return new URLClassLoader(array, parent);
					}
				});
	}

	public static ClassLoader createClassLoader(String name, ClassLoader parent)
			throws IOException {

		List<Repository> repositories = new ArrayList<Repository>();

		StringTokenizer tokenizer = new StringTokenizer(name, ",");
		while (tokenizer.hasMoreElements()) {
			String repository = tokenizer.nextToken().trim();
			if (repository.length() == 0) {
				continue;
			}

			// Local repository
			if (repository.endsWith("*.jar")) {
				repository = repository.substring(0, repository.length()
						- "*.jar".length());
				repositories
						.add(new Repository(repository, RepositoryType.GLOB));
			} else if (repository.endsWith(".jar")) {
				repositories
						.add(new Repository(repository, RepositoryType.JAR));
			} else {
				repositories
						.add(new Repository(repository, RepositoryType.DIR));
			}
		}

		ClassLoader classLoader = ClassLoaderFactory.createClassLoader(
				repositories, parent);

		return classLoader;

	}

	private static boolean validateFile(File file, RepositoryType type) {
		if (RepositoryType.DIR == type || RepositoryType.GLOB == type) {
			if (!file.exists() || !file.isDirectory() || !file.canRead()) {
				return false;
			}
		} else if (RepositoryType.JAR == type) {
			if (!file.exists() || !file.canRead()) {
				return false;
			}
		}
		return true;
	}

	public enum RepositoryType {
		DIR, GLOB, JAR, URL
	}

	public static class Repository {
		private String location;
		private RepositoryType type;

		public Repository(String location, RepositoryType type) {
			this.location = location;
			this.type = type;
		}

		public String getLocation() {
			return location;
		}

		public RepositoryType getType() {
			return type;
		}
	}
}
