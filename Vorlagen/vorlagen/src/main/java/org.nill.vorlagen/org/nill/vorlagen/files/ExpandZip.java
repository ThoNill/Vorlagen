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

import org.nill.vorlagen.generator.Generator;

public class ExpandZip implements Generator {
	private File ausgabeVerzeichnis;
	private File zipDatei;

	public ExpandZip(File ausgabeVerzeichnis, File zipDatei) {
		super();
		this.ausgabeVerzeichnis = ausgabeVerzeichnis;
		this.zipDatei = zipDatei;
	}

	@Override
	public void erzeugeAusgabe() throws Exception {
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
			System.out.println(ex);
		}
	}
}
