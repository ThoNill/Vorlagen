package test.xml;

import static org.junit.Assert.*;

import java.io.File;
import java.io.IOException;

import nu.xom.Document;

import org.junit.Test;
import org.nill.ST.xml.XMLVorlage;
import org.nill.ST.xml.XMLVorlagenFabrik;
import org.nill.modelle.xml.XMLModelFabrik;
import org.nill.vorlagen.Vorlage;

public class TesteXML {

    public TesteXML() {
        // TODO Auto-generated constructor stub
    }

    @Test
    public void erzeugeMaschine() {
        try {
            XMLTestMaschine maschine = new XMLTestMaschine("test", ".", ".",".");
        } catch (Exception ex) {
            fail();
        }
    }

    @Test
    public void ladeXMLDefinition() {
        try {

            XMLTestMaschine maschine = new XMLTestMaschine("test", ".", ".",
                    ".");
            XMLModelFabrik modellFabrik = new XMLModelFabrik();
            Document document = modellFabrik.erzeugeModell(maschine
                    .getBasisVorlagenVerzeichnis()
                    + File.separator
                    + "modelle"
                    + File.separator + "beispiel.xml");
            
            assertNotNull(document);
        } catch (Exception ex) {
            fail();
        }

    }

    @Test
    public void ladeVorlage() {
        try {

            XMLTestMaschine maschine = new XMLTestMaschine("test", ".", ".",
                    ".");
            XMLVorlagenFabrik vorlagenFabrik = new XMLVorlagenFabrik(
                    maschine.getBasisVorlagenVerzeichnis() + File.separator
                            + "vorlagen", null, null);
            Vorlage vorlage = vorlagenFabrik.erzeugeVorlage("beispiel");
            assertNotNull(vorlage);
        } catch (Exception ex) {
            fail();
        }

    }

    @Test
    public void ladeModelUndVorlage() {
        try {

            XMLTestMaschine maschine = new XMLTestMaschine("test", ".", ".",
                    ".");
            XMLModelFabrik modellFabrik = new XMLModelFabrik();
            Document document = modellFabrik.erzeugeModell(maschine
                    .getBasisVorlagenVerzeichnis()
                    + File.separator
                    + "modelle"
                    + File.separator + "beispiel.xml");
            assertNotNull(document);

            XMLVorlagenFabrik vorlagenFabrik = new XMLVorlagenFabrik(
                    maschine.getBasisVorlagenVerzeichnis() + File.separator
                            + "vorlagen", null, null);
            XMLVorlage vorlage = (XMLVorlage) vorlagenFabrik
                    .erzeugeVorlage("beispiel");
            assertNotNull(vorlage);

            String inhalt = vorlage.apply("dateiInhalt", document);
            assertTrue(inhalt.contains("public class beispiel"));
        } catch (Exception ex) {
            fail();
        }

    }

    @Test
    public void ladeModelUndVorlageMitMaschine() {
        XMLTestMaschine maschine = new XMLTestMaschine("modelle", "modelle",
                ".", "ausgabe");
        try {
            maschine.erzeugeAusgabe();
            assertTrue(new File("ausgabe/java/package/beispiel.java").exists());
        } catch (Exception e) {
            e.printStackTrace();
            fail();
        }
    }

}
