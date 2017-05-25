package org.nill.modelle.xml;

import nu.xom.Document;
import org.nill.verzeichnisse.*;


public class XMLVerzeichnisVorlagenModellFabrik extends VerzeichnisVorlagenModellFabrik<Document, VerzeichnisModell>{

    public XMLVerzeichnisVorlagenModellFabrik(String verzeichnis) {
        super(new XMLModelFabrik());
    }

}
