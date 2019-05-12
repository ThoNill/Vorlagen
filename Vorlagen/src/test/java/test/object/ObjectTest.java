package test.object;

import static org.junit.Assert.fail;

import java.io.File;

import org.junit.Test;
import org.nill.object.ModellGenerator;

public class ObjectTest {

	public ObjectTest() {
		// TODO Auto-generated constructor stub
	}

	@Test
	public void teste() {
		try {

			ModellGenerator g = new ModellGenerator(new File("src/test/resources/vorlagen/object/single"), new File("./target/javatest"));
			BeispielBuchung b = new BeispielBuchung();
			BeispielMandant a = new BeispielMandant();
			a.init();
			b.init();
			g.erzeugeAusgabe(a);
			g.erzeugeAusgabe(b);
		} catch (Exception e) {
			e.printStackTrace();
			fail();
		}
	}
}
