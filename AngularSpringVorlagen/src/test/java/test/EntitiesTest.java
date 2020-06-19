package test;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.io.File;
import java.util.Collections;

import org.junit.Test;
import org.nill.vorlagen.compiler.Compiler;
import org.nill.vorlagen.compiler.ConverterVerzeichnis;
import org.nill.vorlagen.compiler.model.ObjectModell;
import org.nill.vorlagen.object.ModellGenerator;

import test.entities.BeispielBuchung;
import test.entities.BeispielMandant;
import test.entities.Verknüpfungen;

public class EntitiesTest {

	public EntitiesTest() {
		// TODO Auto-generated constructor stub
	}

	@Test
	public void teste() {
		try {
	
				ConverterVerzeichnis converter = new ConverterVerzeichnis();

				ObjectModell a = new Compiler().analyse("src/test/java",BeispielBuchung.class,converter); 
				ObjectModell b = new Compiler().analyse("src/test/java",BeispielMandant.class,converter);

				a.addConnection(new Verknüpfungen());
				b.addConnection(new Verknüpfungen());
				new ModellGenerator("src/main/resources/entity/single", "target/generiert/src/main/java/",Collections.singleton(a)).erzeugeAusgabe();
				testeFileExistiert("target/generiert/src/main/java/entities/BeispielBuchung.java");

				new ModellGenerator("src/main/resources/entity/single", "target/generiert/src/main/java/",Collections.singleton(b)).erzeugeAusgabe();				
		} catch (Exception e) {
			e.printStackTrace();
			fail();
		}
	}
	
	public void testeFileExistiert(String filename) {
		File f = new File(filename);
		assertTrue(f.exists());
	}
}
