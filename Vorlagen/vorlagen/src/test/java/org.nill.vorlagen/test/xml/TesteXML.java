package test.xml;

import java.io.File;
import java.nio.charset.StandardCharsets;
import static org.junit.Assert.*;
import org.junit.Test;
import org.nill.vorlagen.generator.file.ModellAndFile;
import org.nill.vorlagen.xml.model.File2Document;
import org.nill.vorlagen.xml.st.DocumentSTConsumer;

import nu.xom.Document;
public class TesteXML {
	private static File basisVerzeicnis = new File("./target");

	public TesteXML() {
		super();
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
					"modelle/beispiel.xml");

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
			Document document = modellFabrik.apply("modelle/beispiel.xml");
			assertNotNull(document);

			DocumentSTConsumer consumer = new DocumentSTConsumer(StandardCharsets.UTF_8,"test.xml.wrap","BeispielWrap");
			
			ModellAndFile<Document> mf1 = new ModellAndFile<Document>(document, "vorlagen/beispiel.stg");
			ModellAndFile<ModellAndFile<Document>> mf2 = new ModellAndFile<>(mf1,"./target/java");
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
