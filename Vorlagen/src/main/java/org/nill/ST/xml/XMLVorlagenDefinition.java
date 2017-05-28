package org.nill.ST.xml;

import java.io.File;
import java.nio.charset.Charset;

import nu.xom.Document;

import org.nill.modelle.xml.XMLVerzeichnisVorlagenModellFabrik;
import org.nill.verzeichnisse.VerzeichnisModell;
import org.nill.vorlagen.VorlagenDefinition;

import test.xml.XMLTestMaschine;

public class XMLVorlagenDefinition extends
        VorlagenDefinition<VerzeichnisModell, String, Document, String> {

    public XMLVorlagenDefinition(
            String vorlagenBeschreibung,
            String modellVerzeichnis,
            String vorlagenVerzeichnis,
            String zielVerzeichnis,
            String packageName,
            String defaultClass,
            XMLTestMaschine maschine,
            Charset charSet) {
        super(vorlagenBeschreibung, modellVerzeichnis, vorlagenVerzeichnis,
                zielVerzeichnis, 
                new XMLVorlagenFabrik(maschine.getBasisVorlagenVerzeichnis() + File.separatorChar  + vorlagenVerzeichnis,packageName,defaultClass),
                new XMLVerzeichnisVorlagenModellFabrik(maschine.getBasisModellVerzeichnis() + File.separator + modellVerzeichnis),
                maschine, charSet);
    }

}
