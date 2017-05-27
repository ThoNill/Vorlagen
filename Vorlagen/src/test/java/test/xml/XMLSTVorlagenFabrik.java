package test.xml;

import nu.xom.Document;

import org.nill.ST.vorlagen.STVorlagenFabrik;
import org.nill.vorlagen.Vorlage;

public class XMLSTVorlagenFabrik extends STVorlagenFabrik<Document>{

    public XMLSTVorlagenFabrik(String dateiName) {
        super(dateiName);
    }

    @Override
    public Vorlage<Document> erzeugeVorlageAusDatei(String dateiName) {
       return new XMLSTVorlage(dateiName);
    }

}
