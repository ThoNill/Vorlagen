package test.xml;

import java.io.File;
import java.nio.charset.StandardCharsets;

import junit.framework.Assert;
import nu.xom.Document;

import org.nill.ST.xml.XMLMaschine;
import org.nill.ST.xml.XMLVorlagenDefinition;
import org.nill.ST.xml.XMLVorlagenFabrik;
import org.nill.modelle.xml.XMLModelFabrik;
import org.nill.modelle.xml.XMLVerzeichnisVorlagenModellFabrik;
import org.nill.verzeichnisse.VerzeichnisModell;
import org.nill.verzeichnisse.VerzeichnisModellFabrik;
import org.nill.vorlagen.VorlagenMaschine;

import static org.junit.Assert.*;

import org.junit.Test;


public class XMLTestMaschine extends XMLMaschine{

    public XMLTestMaschine(String modellBeschreibung,
            String basisModellVerzeichnis, String basisVorlagenVerzeichnis,
            String basisZielVerzeichnis
            ) {
        super(modellBeschreibung, basisModellVerzeichnis, basisVorlagenVerzeichnis,
                basisZielVerzeichnis);
        
        
        new XMLVorlagenDefinition("beispiel", "modelle", "vorlagen",
                "java",this,StandardCharsets.UTF_8);
    
    }


}
