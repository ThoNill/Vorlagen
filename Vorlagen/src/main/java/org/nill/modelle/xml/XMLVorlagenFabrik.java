package org.nill.ST.xml;


import java.io.File;

import org.nill.ST.vorlagen.STVorlagenFabrik;


public class XMLVorlagenFabrik<Document> extends STVorlagenFabrik {
    private String packageName;
    private String defaultClass;
    
    public XMLVorlagenFabrik(String verzeichnis,String packegeName,String defaultClass) {
        super(verzeichnis);
        this.packageName = packegeName;
        this.defaultClass =  defaultClass;
    }

    @Override
    public XMLVorlage erzeugeVorlageAusDatei(String dateiName) {
        return new XMLVorlage(dateiName,packageName, defaultClass);
    }


    
}
