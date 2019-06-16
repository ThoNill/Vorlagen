package org.nill.vorlagen.files;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URISyntaxException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.nill.vorlagen.lists.ListTransform;

public class DateienEinesVerzeichnisses implements ListTransform<String, String> {
	static Logger logger = Logger.getLogger(DateienEinesVerzeichnisses.class.getSimpleName());

	public DateienEinesVerzeichnisses() {
		super();
	}

	@Override
	public List<String> transform(String verzeichnis) {
		List<String> liste = new ArrayList<>();
		try {
			filesFromFileListClassloader(liste, verzeichnis);
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


	public void filesFromFileListClassloader(List<String> plist, final String directory)
			throws IOException, URISyntaxException {
		ClassLoader loader = Thread.currentThread().getContextClassLoader();
		String fileListName = directory + "/filelist.list";
		File f = new File(fileListName);
		if (f.exists()) {
			logger.log(Level.INFO, "File " + f.getAbsolutePath() +" exists");
		}
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


}
