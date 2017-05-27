package org.nill.ST.xml;


import org.nill.ST.vorlagen.STVorlagenFabrik;


public class XMLVorlagenFabrik<Document> extends STVorlagenFabrik {
  
    public XMLVorlagenFabrik(String verzeichnis) {
        super(verzeichnis);
    }

    @Override
    public XMLVorlage erzeugeVorlageAusDatei(String dateiName) {
        return new XMLVorlage(dateiName, getVerzeichnis());
    }


    
}
