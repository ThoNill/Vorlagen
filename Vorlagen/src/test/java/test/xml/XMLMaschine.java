package test.xml;

import java.io.File;
import java.nio.charset.StandardCharsets;

import nu.xom.Document;

import org.nill.ST.STVorlagenFabrik;
import org.nill.modelle.xml.XMLVerzeichnisVorlagenModellFabrik;
import org.nill.verzeichnisse.*;
import org.nill.vorlagen.ModellFabrik;
import org.nill.vorlagen.VorlagenMaschine;

import test.TestVorlagenDefinition;
import test.TestVorlagenFabrik;
import test.TestVorlagenModelFabrik;

public class XMLMaschine extends VorlagenMaschine<VerzeichnisModell,String,Document, String>{

    public XMLMaschine(String modellBeschreibung,
            String basisModellVerzeichnis, String basisVorlagenVerzeichnis,
            String basisZielVerzeichnis
            ) {
        super(modellBeschreibung, basisModellVerzeichnis, basisVorlagenVerzeichnis,
                basisZielVerzeichnis, new VerzeichnisModellFabrik());
        
        
        new XMLVorlagenDefinition("testVorlage", ".", ".",
                "java", new XMLSTVorlagenFabrik(basisModellVerzeichnis + File.separator + "xmlTest"),
                new XMLVerzeichnisVorlagenModellFabrik(basisModellVerzeichnis),this, StandardCharsets.UTF_8);
    
    }

  
}
