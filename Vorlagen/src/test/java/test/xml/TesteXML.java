package test.xml;

//import janusAngularj2Frontend.JanusAngular2Maschine;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.io.File;

import org.junit.Test;
import org.nill.ST.xml.XML_STVorlage;
import org.nill.ST.xml.XML_STVorlagenFabrik;
import org.nill.modelle.xml.XMLModelFabrik;
import org.nill.vorlagen.Vorlage;

import nu.xom.Document;

public class TesteXML {
	private static File basisVerzeicnis = new File("./target");

	public TesteXML() {
		// TODO Auto-generated constructor stub
	}

	@Test
	public void erzeugeMaschine() {
		try {
			XMLTestMaschine maschine = new XMLTestMaschine();
		} catch (Exception ex) {
			fail();
		}
	}

	@Test
	public void ladeXMLDefinition() {
		try {

			XMLTestMaschine maschine = new XMLTestMaschine();
			XMLModelFabrik modellFabrik = new XMLModelFabrik();
			Document document = modellFabrik.erzeugeModell(
					maschine.getModellVerzeichnis().toString() + File.separator + "beispiel.xml");

			assertNotNull(document);
		} catch (Exception ex) {
			ex.printStackTrace();
			fail();
		}

	}

	@Test
	public void ladeVorlage() {
		try {

			XMLTestMaschine maschine = new XMLTestMaschine();
			XML_STVorlagenFabrik vorlagenFabrik = new XML_STVorlagenFabrik(
					maschine.getVorlageVerzeichnis(),basisVerzeicnis,"test.xml.wrap","BeispielWrap",true);
			Vorlage vorlage = vorlagenFabrik.erzeugeVorlage(new File(maschine.getVorlageVerzeichnis(),"beispiel.stg"));
			assertNotNull(vorlage);
		} catch (Exception ex) {
			ex.printStackTrace();
			fail();
		}

	}

	@Test
	public void ladeModelUndVorlage() {
		try {

			XMLTestMaschine maschine = new XMLTestMaschine();
			XMLModelFabrik modellFabrik = new XMLModelFabrik();
			Document document = modellFabrik.erzeugeModell(new File(maschine.getModellVerzeichnis(),"beispiel.xml").toString());
			assertNotNull(document);

			XML_STVorlagenFabrik vorlagenFabrik = new XML_STVorlagenFabrik(maschine.getVorlageVerzeichnis(),basisVerzeicnis,"test.xml.wrap","BeispielWrap",true);
			XML_STVorlage vorlage = (XML_STVorlage) vorlagenFabrik.erzeugeVorlage(new File(maschine.getVorlageVerzeichnis(),"beispiel.stg"));
			assertNotNull(vorlage);

			String inhalt = vorlage.apply("dateiInhalt", document);
			assertTrue(inhalt.contains("public class beispiel"));
		} catch (Exception ex) {
			ex.printStackTrace();
			fail();
		}

	}

	@Test
	public void ladeModelUndVorlageMitMaschine() {
		try {
			XMLTestMaschine maschine = new XMLTestMaschine();
			maschine.erzeugeAusgabe();
			assertTrue(new File("./target/java/package/beispiel.java").exists());
		} catch (Exception e) {
			e.printStackTrace();
			fail();
		}
	}

	/*
	 * @Test public void erzeugeJanusAngular2() { XMLMaschine maschine = new
	 * JanusAngular2Maschine("modelle", "modelle", ".", "ausgabe"); try {
	 * maschine.erzeugeAusgabe(); assertTrue(new
	 * File("ausgabe/ts/frontend/beispielPage.html").exists()); } catch (Exception
	 * e) { e.printStackTrace(); fail(); } }
	 */

}
