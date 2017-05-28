package org.nill.verzeichnisse;

import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.file.FileSystem;
import java.nio.file.FileSystems;
import java.nio.file.FileVisitResult;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.nill.vorlagen.ModellFabrik;
import org.nill.vorlagen.VorlagenModellFabrik;

public class VerzeichnisVorlagenModellFabrik<VORLAGEN_MODELL, MODELL extends MitVerzeichnis>
        implements VorlagenModellFabrik<VORLAGEN_MODELL, MODELL> {
    private ModellFabrik<VORLAGEN_MODELL, String> modellFabrik;

    public VerzeichnisVorlagenModellFabrik(
            ModellFabrik<VORLAGEN_MODELL, String> modellFabrik) {
        super();
        this.modellFabrik = modellFabrik;
    }

    @Override
    public List<VORLAGEN_MODELL> erzeugeVorlagenModelle(MODELL mitVerzeichnis)
            throws Exception {
        List<VORLAGEN_MODELL> liste = new ArrayList<VORLAGEN_MODELL>();
        try {
            alleDateien(liste, mitVerzeichnis.getVerzeichnis());
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
        return liste;
    }

    private void alleDateien(List<VORLAGEN_MODELL> liste, String verzeichnis)
            throws IOException, URISyntaxException {

        Path start = bestimmeStartVerzeichnis(verzeichnis);
        verzeichnisDurcharbeiten(liste, verzeichnis, start);
    }

    protected Path bestimmeStartVerzeichnis(String verzeichnis)
            throws URISyntaxException, IOException {
        Path start = Paths.get(verzeichnis);
        File startFile = start.toFile();
        if (!startFile.exists()) {
            URI uri = Thread.currentThread().getContextClassLoader()
                    .getResource(verzeichnis).toURI();
            if (uri.getScheme().equals("jar")) {
                FileSystem fileSystem = FileSystems.newFileSystem(uri,
                        Collections.<String, Object> emptyMap());
                start = fileSystem.getPath("/" + verzeichnis);
                startFile = start.toFile();
            }
        }
        return start;
    }

    protected void verzeichnisDurcharbeiten(List<VORLAGEN_MODELL> liste,
            String verzeichnis, Path start) throws IOException {
        File startFile = start.toFile();
        if (startFile.exists() && startFile.isDirectory()) {
            Files.walkFileTree(start, new SimpleFileVisitor<Path>() {
                @Override
                public FileVisitResult visitFile(Path file,
                        BasicFileAttributes attrs) throws IOException {
                    VORLAGEN_MODELL modell = modellFabrik
                            .erzeugeModell(file.toString());
                    liste.add(modell);
                    return FileVisitResult.CONTINUE;
                }
            });
        }
    }

}
