package org.nill.verzeichnisse;

import java.io.File;
import java.io.IOException;
import java.nio.file.FileVisitResult;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.ArrayList;
import java.util.List;

public class DateienEinesVerzeichnisses {
	private File verzeichnis;

	public DateienEinesVerzeichnisses(File verzeichnis) {
		super();
		this.verzeichnis = verzeichnis;
	}

	public List<File> erzeugeFileListe() throws Exception {
		List<File> liste = new ArrayList<>();
		try {
			verzeichnisDurcharbeiten(liste, verzeichnis.toPath());
		} catch (IOException e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}
		return liste;
	}

	private void verzeichnisDurcharbeiten(List<File> liste, Path start) throws IOException {
		File startFile = start.toFile();
		if (startFile.exists()) {
			if (startFile.isDirectory()) {
				addDateienImVerzeichnis(liste, start);
			} else {
				liste.add(startFile);
			}
		}

	}

	private void addDateienImVerzeichnis(List<File> liste, Path start) throws IOException {
		Files.walkFileTree(start, new SimpleFileVisitor<Path>() {
			@Override
			public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
				Path filePath = file.getFileName();
				if (filePath != null) {
					liste.add(new File(verzeichnis, filePath.toString()));
				}
				return FileVisitResult.CONTINUE;
			}
		});
	}

}
