package org.nill.ST.xml;

import java.io.File;
import java.nio.charset.StandardCharsets;

import nu.xom.Document;

import org.nill.ST.xml.XMLVorlagenDefinition;
import org.nill.modelle.xml.XMLVerzeichnisVorlagenModellFabrik;
import org.nill.verzeichnisse.VerzeichnisModell;
import org.nill.verzeichnisse.VerzeichnisModellFabrik;
import org.nill.vorlagen.VorlagenMaschine;

public class XMLMaschine extends VorlagenMaschine<VerzeichnisModell,String,Document, String>{

    public XMLMaschine(String modellBeschreibung,
            String basisModellVerzeichnis, String basisVorlagenVerzeichnis,
            String basisZielVerzeichnis
            ) {
        super(modellBeschreibung, basisModellVerzeichnis, basisVorlagenVerzeichnis,
                basisZielVerzeichnis, new VerzeichnisModellFabrik());
   
    }

  
}
