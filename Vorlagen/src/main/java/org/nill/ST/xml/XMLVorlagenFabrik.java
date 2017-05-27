package org.nill.ST.xml;


import java.io.File;

import org.nill.ST.vorlagen.STVorlagenFabrik;
import org.nill.vorlagen.Vorlage;
import org.nill.vorlagen.VorlagenFabrik;
import nu.xom.Document;


public class XMLVorlagenFabrik<Document> extends STVorlagenFabrik {
  
    public XMLVorlagenFabrik(String verzeichnis) {
        super(verzeichnis);
    }

    @Override
    public XMLVorlage erzeugeVorlageAusDatei(String dateiName) {
        return new XMLVorlage(dateiName, getVerzeichnis());
    }


    
}
