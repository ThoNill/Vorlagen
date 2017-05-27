package org.nill.ST.xml;


import java.io.File;

import org.nill.ST.vorlagen.STVorlagenFabrik;


public class XMLVorlagenFabrik<Document> extends STVorlagenFabrik {
    private String packageName;
    
    public XMLVorlagenFabrik(String verzeichnis,String packegeName) {
        super(verzeichnis);
        this.packageName = packegeName;
    }

    @Override
    public XMLVorlage erzeugeVorlageAusDatei(String dateiName) {
        return new XMLVorlage(dateiName,packageName);
    }


    
}
