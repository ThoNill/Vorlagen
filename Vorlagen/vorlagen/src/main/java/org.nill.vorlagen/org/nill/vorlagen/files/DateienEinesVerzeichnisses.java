package org.nill.vorlagen.files;

import java.io.File;
import java.io.IOException;
import java.nio.file.FileVisitOption;
import java.nio.file.FileVisitResult;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.ArrayList;
import java.util.EnumSet;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.nill.vorlagen.lists.ListTransform;

public class DateienEinesVerzeichnisses implements ListTransform<File, File> {
	static Logger logger = Logger.getLogger(DateienEinesVerzeichnisses.class.getSimpleName());

	public DateienEinesVerzeichnisses() {
		super();
	}

	@Override
	public List<File> transform(File verzeichnis) {
		List<File> liste = new ArrayList<>();
		try {
			verzeichnisDurcharbeiten(liste, verzeichnis);
		} catch (IOException e) {
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
			logger.log(Level.SEVERE,() -> "Verzeichnis " + startFile + " existiert nicht");
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
							if (!f.isDirectory()) {
								liste.add(f);
							}
						}
						return FileVisitResult.CONTINUE;
					}
				});
	}

}
