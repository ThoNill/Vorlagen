package test;

import static org.junit.Assert.fail;

import org.junit.Test;

import janusAngularj2Frontend.JanusAngular2Maschine;

public class AngularTest {

	@Test
	public void testAngular() {
		try {
			JanusAngular2Maschine a = new JanusAngular2Maschine(
					"modelle", 
					"angular");
			a.erzeugeAusgabe();
		} catch (Exception e) {
			e.printStackTrace();
			fail();
		}

	}


}
