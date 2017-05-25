package org.nill.verzeichnisse;

import java.io.IOException;
import java.nio.file.FileVisitResult;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.ArrayList;
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
    public List<VORLAGEN_MODELL> erzeugeVorlagenModelle(MODELL mitVerzeichnis) {
        List<VORLAGEN_MODELL> liste = new ArrayList<VORLAGEN_MODELL>();
        try {
            alleDateien(liste, mitVerzeichnis.getVerzeichnis());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return liste;
    }

    private void alleDateien(List<VORLAGEN_MODELL> liste, String verzeichnis)
            throws IOException {

        Path start = Paths.get(verzeichnis);
        Files.walkFileTree(start, new SimpleFileVisitor<Path>() {
            @Override
            public FileVisitResult visitFile(Path file,
                    BasicFileAttributes attrs) throws IOException {
                String name = file.getFileName().toString()
                        .replaceAll(".java", "");
                VORLAGEN_MODELL modell = modellFabrik.erzeugeModell(name);
                liste.add(modell);
                return FileVisitResult.CONTINUE;
            }
        });
    }

}
