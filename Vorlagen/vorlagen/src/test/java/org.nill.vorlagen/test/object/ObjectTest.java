package test.object;

import static org.junit.Assert.*;
import java.util.Collections;

import org.junit.Test;
import org.nill.vorlagen.compiler.Compiler;
import org.nill.vorlagen.compiler.ConverterVerzeichnis;
import org.nill.vorlagen.compiler.model.ObjectModell;
import org.nill.vorlagen.object.ModellGenerator;

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

			// SOURCE_TESTDIR +"src/test/resources/org.nill.vorlagen/vorlagen/object"
			
			ModellGenerator g = new ModellGenerator("vorlagen/object/single", "../generiert/src/main/java/",Collections.emptyList());
			ObjectModell a = new Compiler().analyse(SOURCE_TESTDIR +"src/test/java/org.nill.vorlagen",BeispielBuchung.class,converter); 
			ObjectModell b = new Compiler().analyse(SOURCE_TESTDIR +"src/test/java/org.nill.vorlagen",BeispielMandant.class,converter);
		//	ObjectModell v = new Compiler().analyse(new File("src/test/java/org.nill.vorlagen"),Verkn�pfungen.class);

			a.addConnection(new Verknüpfungen());
			b.addConnection(new Verknüpfungen());
			g.erzeugeAusgabe(a);
			g.erzeugeAusgabe(b);
			ObjectModell s = new Compiler().analyse(SOURCE_TESTDIR +"src/test/java/org.nill.vorlagen",BeispielService.class,converter);
			g.erzeugeAusgabe(s);
		} catch (Exception e) {
			e.printStackTrace();
			fail();
		}
	}
}
