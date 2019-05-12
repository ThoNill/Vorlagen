package test.xml;

//import janusAngularj2Frontend.JanusAngular2Maschine;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.io.File;
import java.nio.charset.StandardCharsets;

import org.junit.Test;
import org.nill.ST.xml.DocumentSTConsumer;
import org.nill.files.ModellAndFile;
import org.nill.modelle.xml.File2Document;

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
		File2Document modellFabrik = new File2Document();
			Document document = modellFabrik.apply(
					new File("src/test/resources/modelle/beispiel.xml"));

			assertNotNull(document);
		} catch (Exception ex) {
			ex.printStackTrace();
			fail();
		}

	}

	

	@Test
	public void ladeModelUndVorlage() {
		try {
			File2Document modellFabrik = new File2Document();
			Document document = modellFabrik.apply(new File("src/test/resources/modelle/beispiel.xml"));
			assertNotNull(document);

			DocumentSTConsumer consumer = new DocumentSTConsumer(StandardCharsets.UTF_8,"test.xml.wrap","BeispielWrap");
			
			ModellAndFile<Document> mf1 = new ModellAndFile<Document>(document, new File("src/test/resources/vorlagen/beispiel.stg"));
			ModellAndFile<ModellAndFile<Document>> mf2 = new ModellAndFile<>(mf1,new File("./target/java"));
			consumer.accept(mf2);

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



}
