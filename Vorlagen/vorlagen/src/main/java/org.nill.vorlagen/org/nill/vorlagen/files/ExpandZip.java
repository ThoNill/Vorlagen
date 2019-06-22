package org.nill.vorlagen.files;

import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.nio.file.FileSystem;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Logger;

import org.nill.vorlagen.generator.Generator;
import org.nill.vorlagen.vorlagen.RuntimeVorlagenException;

public class ExpandZip implements Generator {
	static Logger logger = Logger.getLogger(ExpandZip.class.getSimpleName());

	private File ausgabeVerzeichnis;
	private File zipDatei;

	public ExpandZip(File ausgabeVerzeichnis, File zipDatei) {
		super();
		this.ausgabeVerzeichnis = ausgabeVerzeichnis;
		this.zipDatei = zipDatei;
	}

	@Override
	public void erzeugeAusgabe(){
		Path zipFile = zipDatei.toPath();
		Path absPath = zipFile.toAbsolutePath().normalize(); // Windowspfad
		URI uri = URI.create("jar:file:///" + absPath.toString().replace('\\', '/')); // jar: ist notwendig
		Path dest = ausgabeVerzeichnis.toPath();

		Map<String, String> env = new HashMap<>();
		env.put("create", "true");
		try (FileSystem zipFileSystem = FileSystems.newFileSystem(uri, env)) {
			Path root = zipFileSystem.getPath("/");
			Files.walkFileTree(root, new ExtractZipFileVisitor(dest));
		} catch (IOException ex) {
			throw new RuntimeVorlagenException( "Error at ExpandZip",ex);
		}
	}
}
