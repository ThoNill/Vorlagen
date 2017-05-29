package org.nill.modelle.xml;

import nu.xom.Document;

import org.nill.verzeichnisse.VerzeichnisModell;
import org.nill.verzeichnisse.VerzeichnisVorlagenModellFabrik;
import org.nill.vorlagen.ModellFabrik;


public class XMLVerzeichnisVorlagenModellFabrik extends VerzeichnisVorlagenModellFabrik<Document, VerzeichnisModell>{

    public XMLVerzeichnisVorlagenModellFabrik() {
        this(new XMLModelFabrik());
    }

    public XMLVerzeichnisVorlagenModellFabrik(ModellFabrik<Document, String> modellFabrik) {
        super(modellFabrik);
    }
   
}
