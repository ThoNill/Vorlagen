package org.nill.vorlagen.files;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URISyntaxException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.nill.vorlagen.vorlagen.RuntimeVorlagenException;

public class DateienEinesVerzeichnisses implements Function<String, List<String>> {
	static Logger logger = Logger.getLogger(DateienEinesVerzeichnisses.class.getSimpleName());

	public DateienEinesVerzeichnisses() {
		super();
	}

	@Override
	public List<String> apply(String verzeichnis) {
		List<String> liste = new ArrayList<>();
		try {
			filesFromFileListClassloader(liste, verzeichnis);
			if (liste.isEmpty()) {
				logger.log(Level.INFO, "No entries found in classpath {0} ",verzeichnis);
			} else {
				liste.stream().forEach(x -> logger.log(Level.INFO, "File {0}",x));
			}
		} catch (IOException | URISyntaxException  e) {
			throw new RuntimeVorlagenException( "Error file search in directory "+verzeichnis,e);
		}
		return liste;
	}


	public void filesFromFileListClassloader(List<String> plist, final String directory)
			throws IOException, URISyntaxException {
		ClassLoader loader = Thread.currentThread().getContextClassLoader();
		String fileListName = directory + "/filelist.list";

		logger.log(Level.INFO, "fileListName {0}",fileListName);
		InputStream in = loader.getResourceAsStream(fileListName);
		if (in == null) {
			logger.log(Level.INFO, "nicht im Classpath! Suche im Filesystem nach {0}",fileListName);
			checkFileInFilesystem(fileListName);

			in = new FileInputStream(fileListName);
		}
		logger.log(Level.INFO, "Stream: {0}",in);
		try (BufferedReader reader = new BufferedReader(new InputStreamReader(in, StandardCharsets.UTF_8))){
			reader.lines()
			.map(f1 -> f1.trim()).filter(f2 -> !f2.startsWith("#"))
					.forEach(f3 -> plist.add(directory + "/" + f3));
		} 
	}

	private void checkFileInFilesystem(String fileListName) {
		File f = new File(fileListName);
		if (!f.exists()){
			throw new RuntimeVorlagenException(" Die Datei " + f.getAbsolutePath() + " existiert nicht ");
		}
		if (!f.canRead()){
			throw new RuntimeVorlagenException(" Die Datei " + f.getAbsolutePath() + " kann nicht gelesen werden ");
		}
		if (f.isDirectory()){
			throw new RuntimeVorlagenException(" Die Datei " + f.getAbsolutePath() + " ist ein Verzeichnis ");
		}
	}


}
