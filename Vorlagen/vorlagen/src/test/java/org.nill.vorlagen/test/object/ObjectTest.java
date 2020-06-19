package test.object;

import java.io.File;
import java.util.Collections;

import org.junit.Test;
import org.nill.vorlagen.compiler.Compiler;
import org.nill.vorlagen.compiler.ConverterVerzeichnis;
import org.nill.vorlagen.compiler.model.ObjectModell;
import org.nill.vorlagen.object.ModellGenerator;
import static org.junit.Assert.fail;
import static org.junit.Assert.assertTrue;

public class ObjectTest {
	private static final String SOURCE_TESTDIR = "C:/Users/tnill/git/Vorlagen/Vorlagen/vorlagen/";
	

	public ObjectTest() {
		super();
	}

	@Test
	public void teste() {
		try {
			ConverterVerzeichnis converter = new ConverterVerzeichnis();
			converter.put(MonatJahr.class, MonatJahrAdapter.class);
			
			ObjectModell a = new Compiler().analyse(SOURCE_TESTDIR +"src/test/java/org.nill.vorlagen",BeispielBuchung.class,converter); 
			ObjectModell b = new Compiler().analyse(SOURCE_TESTDIR +"src/test/java/org.nill.vorlagen",BeispielMandant.class,converter);

			a.addConnection(new Verknüpfungen());
			b.addConnection(new Verknüpfungen());
	
			new ModellGenerator("vorlagen/object/single", "target/generiert/src/main/java/",Collections.singleton(a)).erzeugeAusgabe();
			testeFileExistiert("target/generiert/src/main/java/entities/BeispielBuchung.java");
			new ModellGenerator("vorlagen/object/single", "target/generiert/src/main/java/",Collections.singleton(b)).erzeugeAusgabe();
			testeFileExistiert("target/generiert/src/main/java/entities/BeispielMandant.java");
			ObjectModell s = new Compiler().analyse(SOURCE_TESTDIR +"src/test/java/org.nill.vorlagen",BeispielService.class,converter);
			new ModellGenerator("vorlagen/object/single", "target/generiert/src/main/java/",Collections.singleton(s)).erzeugeAusgabe();
			testeFileExistiert("target/generiert/src/main/java/service/BeispielServiceRestBasis.java");
			testeFileExistiert("target/generiert/src/main/java/service/BeispielServiceRestService.java");
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
