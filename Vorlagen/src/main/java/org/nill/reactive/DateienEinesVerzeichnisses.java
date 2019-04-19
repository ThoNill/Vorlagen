package org.nill.reactive;

import java.io.File;
import java.io.IOException;
import java.nio.file.FileVisitResult;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.ArrayList;
import java.util.List;

public class DateienEinesVerzeichnisses implements ListTransform<File, File> {

	public DateienEinesVerzeichnisses() {
		super();
	}

	@Override
	public List<File> transform(File verzeichnis) {
		List<File> liste = new ArrayList<>();
		try {
			verzeichnisDurcharbeiten(liste, verzeichnis);
		} catch (IOException e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}
		return liste;
	}

	private void verzeichnisDurcharbeiten(List<File> liste,File startFile) throws IOException {
		if (startFile.exists()) {
			if (startFile.isDirectory()) {
				addDateienImVerzeichnis(liste, startFile);
			} else {
				liste.add(startFile);
			}
		}

	}

	private void addDateienImVerzeichnis(List<File> liste, File verzeichnis) throws IOException {
		Files.walkFileTree(verzeichnis.toPath(), new SimpleFileVisitor<Path>() {
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
