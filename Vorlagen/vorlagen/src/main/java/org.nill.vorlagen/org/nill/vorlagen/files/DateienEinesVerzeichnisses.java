package org.nill.vorlagen.files;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.net.URLClassLoader;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.FileSystem;
import java.nio.file.FileSystemNotFoundException;
import java.nio.file.FileSystems;
import java.nio.file.FileVisitOption;
import java.nio.file.FileVisitResult;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.attribute.BasicFileAttributes;
import java.security.CodeSource;
import java.util.ArrayList;
import java.util.Collections;
import java.util.EnumSet;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Stream;

import org.nill.vorlagen.lists.ListTransform;

public class DateienEinesVerzeichnisses implements ListTransform<String, String> {
	static Logger logger = Logger.getLogger(DateienEinesVerzeichnisses.class.getSimpleName());
	private Class<?> ankerClass;

	public DateienEinesVerzeichnisses(Class<?> ankerClass) {
		super();
		this.ankerClass = ankerClass;
	}

	@Override
	public List<String> transform(String verzeichnis) {
		List<String> liste = new ArrayList<>();
		try {
			/*
			 * verzeichnisDurcharbeiten(liste, verzeichnis); if (liste.isEmpty()) {
			 * files(liste, verzeichnis, ankerClass); } if (liste.isEmpty()) { //
			 * filesFromClassLoader(liste,verzeichnis,ankerClass); } if (liste.isEmpty()) {
			 * filesFromFileList(liste, verzeichnis); }
			 */
			if (liste.isEmpty()) {
				filesFromFileListClassloader(liste, verzeichnis, ankerClass);
			}
			if (liste.isEmpty()) {
				logger.log(Level.INFO, "Keine Dateien gefunden in " + verzeichnis);
			} else {
				liste.stream().forEach(x -> logger.log(Level.INFO, "Datei " + x));
			}
		} catch (IOException | URISyntaxException e) {
			throw new IllegalArgumentException(e);
		}
		return liste;
	}

	private void verzeichnisDurcharbeiten(List<File> liste, File startFile) throws IOException {
		if (startFile.exists()) {
			if (startFile.isDirectory()) {
				addDateienImVerzeichnis(liste, startFile);
			} else {
				liste.add(startFile);
			}
		} else {
			logger.log(Level.SEVERE, () -> "Verzeichnis " + startFile.getAbsolutePath() + " existiert nicht");
		}

	}

	private void addDateienImVerzeichnis(List<File> liste, File verzeichnis) throws IOException {
		Files.walkFileTree(verzeichnis.toPath(), EnumSet.noneOf(FileVisitOption.class), 1,
				new SimpleFileVisitor<Path>() {
					@Override
					public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
						Path filePath = file.getFileName();
						if (filePath != null) {
							File f = new File(verzeichnis, filePath.toString());
							if (!f.isDirectory() && !"filelist.list".equals(f.getName())) {
								liste.add(f);
							}
						}
						return FileVisitResult.CONTINUE;
					}
				});
	}

	public void files(List<File> plist, File f, Class<?> ankerClass) throws IOException, URISyntaxException {
		final CodeSource source = ankerClass.getClass().getProtectionDomain().getCodeSource();
		if (source == null) {
			logger.log(Level.INFO, "source ist null");
		} else {
			if (source.getLocation() == null) {
				logger.log(Level.INFO, "location ist null");
			} else {

				URI zipUri = source.getLocation().toURI();
				try (FileSystem zipFS = FileSystems.newFileSystem(zipUri, Collections.EMPTY_MAP)) {
					Path zipPath = zipFS.getPath(f.getPath());
					if (Files.isDirectory(zipPath)) {
						Files.list(zipPath).filter(f1 -> !Files.isDirectory(f1) && !f1.endsWith("filelist.list"))
								.forEach(f2 -> plist.add(f2.toFile()));
					} else {
						plist.add(zipPath.toFile());
					}

				}
			}
		}
	}

	public void filesFromClassLoader(List<File> plist, File f, Class<?> ankerClass)
			throws IOException, URISyntaxException {
		final ClassLoader loader = ankerClass.getClassLoader();

		if (loader instanceof URLClassLoader) {
			URLClassLoader urlLoader = (URLClassLoader) loader;
			for (URL url : urlLoader.getURLs()) {
				URI zipUri = url.toURI();
				try (FileSystem zipFS = FileSystems.newFileSystem(zipUri, Collections.EMPTY_MAP)) {
					Path zipPath = zipFS.getPath(f.getPath());
					if (Files.isDirectory(zipPath)) {
						Files.list(zipPath).filter(f1 -> !Files.isDirectory(f1) && !f1.endsWith("filelist.list"))
								.forEach(f2 -> plist.add(f2.toFile()));
					} else {
						plist.add(zipPath.toFile());
					}
				}
			}
		} else {
			logger.log(Level.INFO, "kein URLClassLoader " + ankerClass.getClassLoader().getClass().getCanonicalName());
		}
	}

	public void filesFromFileList(List<File> plist, File directory) throws IOException, URISyntaxException {
		if (directory.isDirectory()) {
			File fileList = new File(directory, "filelist.list");
			if (fileList.exists()) {

				try (Stream<String> stream = Files.lines(fileList.toPath())) {
					stream.map(f1 -> f1.trim()).filter(f2 -> !f2.startsWith("#"))
							.forEach(f3 -> plist.add(new File(directory, f3)));
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}

	public void filesFromFileListClassloader(List<String> plist, final String directory, Class ankerClass)
			throws IOException, URISyntaxException {
		ClassLoader loader = Thread.currentThread().getContextClassLoader(); // ankerClass.getClassLoader();
		String fileListName = directory + "/filelist.list";
		logger.log(Level.INFO, "fileListName " + fileListName);
		InputStream in = loader.getResourceAsStream(fileListName);
		logger.log(Level.INFO, "Stream: " + in);
		if (in != null) {
			BufferedReader reader = new BufferedReader(new InputStreamReader(in, StandardCharsets.UTF_8));
			reader.lines()
			.map(f1 -> f1.trim()).filter(f2 -> !f2.startsWith("#"))
					.forEach(f3 -> plist.add(directory + "/" + f3));

			reader.close();
		}
	}

	private FileSystem initFileSystem(URI uri) throws IOException {
		try {
			return FileSystems.getFileSystem(uri);
		} catch (FileSystemNotFoundException e) {
			Map<String, String> env = new HashMap<>();
			env.put("create", "true");
			return FileSystems.newFileSystem(uri, env);
		}
	}
}
