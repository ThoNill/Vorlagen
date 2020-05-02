package test;

import static org.junit.Assert.fail;

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
/*			ModellGenerator g = new ModellGenerator(new File("src/main/resources/entity"), new File("./target/src/java/"));
			BeispielBuchung b = new BeispielBuchung();
			BeispielMandant a = new BeispielMandant();
			g.erzeugeAusgabe(a);
			g.erzeugeAusgabe(b);
	*/		
				ConverterVerzeichnis converter = new ConverterVerzeichnis();

				ModellGenerator g = new ModellGenerator("src/main/resources/entity/single", "../generiert/src/main/java/",Collections.emptyList());
				ObjectModell a = new Compiler().analyse("src/test/java",BeispielBuchung.class,converter); 
				ObjectModell b = new Compiler().analyse("src/test/java",BeispielMandant.class,converter);

				a.addConnection(new Verknüpfungen());
				b.addConnection(new Verknüpfungen());
				g.erzeugeAusgabe(a);
				g.erzeugeAusgabe(b);
					
		} catch (Exception e) {
			e.printStackTrace();
			fail();
		}
	}
}
