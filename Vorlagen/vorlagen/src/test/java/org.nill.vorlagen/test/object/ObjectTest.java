package test.object;

import java.io.File;

import org.junit.Test;
import static org.junit.Assert.*;
import org.nill.vorlagen.compiler.Compiler;
import org.nill.vorlagen.compiler.ConverterVerzeichnis;
import org.nill.vorlagen.compiler.model.ObjectModell;
import org.nill.vorlagen.object.ModellGenerator;

public class ObjectTest {

	public ObjectTest() {
		// TODO Auto-generated constructor stub
	}

	@Test
	public void teste() {
		try {
			ConverterVerzeichnis converter = new ConverterVerzeichnis();
			converter.put(MonatJahr.class, MonatJahrAdapter.class);

			ModellGenerator g = new ModellGenerator("vorlagen/object", "../generiert/src/main/java/",ObjectTest.class);
			ObjectModell a = new Compiler().analyse("src/test/java/org.nill.vorlagen",BeispielBuchung.class,converter); 
			ObjectModell b = new Compiler().analyse("src/test/java/org.nill.vorlagen",BeispielMandant.class,converter);
		//	ObjectModell v = new Compiler().analyse(new File("src/test/java/org.nill.vorlagen"),Verknüpfungen.class);

			a.addConnection(new Verknüpfungen());
			b.addConnection(new Verknüpfungen());
			g.erzeugeAusgabe(a);
			g.erzeugeAusgabe(b);
			ObjectModell s = new Compiler().analyse("src/test/java/org.nill.vorlagen",BeispielService.class,converter);
			g.erzeugeAusgabe(s);
		} catch (Exception e) {
			e.printStackTrace();
			fail();
		}
	}
}
