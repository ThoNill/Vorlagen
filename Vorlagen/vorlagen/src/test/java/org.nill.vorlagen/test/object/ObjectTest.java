package test.object;

import org.junit.Test;
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

			ModellGenerator g = new ModellGenerator("vorlagen/object", "../generiert/src/main/java/");
			ObjectModell a = new Compiler().analyse("src/test/java/org.nill.vorlagen",BeispielBuchung.class,converter); 
			ObjectModell b = new Compiler().analyse("src/test/java/org.nill.vorlagen",BeispielMandant.class,converter);
		//	ObjectModell v = new Compiler().analyse(new File("src/test/java/org.nill.vorlagen"),Verkn�pfungen.class);

			a.addConnection(new Verkn�pfungen());
			b.addConnection(new Verkn�pfungen());
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