package test.xml;

import java.io.File;

import org.junit.Test;
import static org.junit.Assert.*;
import org.nill.vorlagen.ST.xml.DocumentSTGenerator;
import org.nill.vorlagen.files.ExpandZip;

import janusAngularj2Frontend.JanusAngular2Maschine;

public class TestReactive {

	@Test
	public void test() {

		DocumentSTGenerator a = new DocumentSTGenerator(

				new File(".", "src/test/resources/modelle"), new File(".", "src/test/resources/vorlagen"),
				new File("./target/reactive"), "test.xml.wrap", "BeispielWrap");
		try {
			a.erzeugeAusgabe();
		} catch (Exception e) {
			fail();
		}

	}
	@Test
	public void testAngular() {
		try {
			JanusAngular2Maschine a = new JanusAngular2Maschine(
					
					new File(".", "src/test/resources/modelle"), 
					new File(".", "src/test/resources/vorlagen"));
			a.erzeugeAusgabe();
		} catch (Exception e) {
			fail();
		}

	}

	@Test
	public void testZip() {
		try {
			ExpandZip a = new ExpandZip(
					
					new File(".", "target/angular7/src"), 
					new File(".", "src/test/resources/angular7/angular7.zip"));
			a.erzeugeAusgabe();
		} catch (Exception e) {
			fail();
		}

	}

}
