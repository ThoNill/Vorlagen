package test.xml;

import java.io.File;

import org.junit.Test;
import org.nill.vorlagen.files.ExpandZip;
import org.nill.vorlagen.xml.st.DocumentSTGenerator;

import janusAngularj2Frontend.JanusAngular2Maschine;

public class TestReactive {

	@Test
	public void test() {

		DocumentSTGenerator a = new DocumentSTGenerator(

				"modelle", "vorlagen", "./target/reactive", "test.xml.wrap",
				"BeispielWrap");
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

					"modelle", "vorlagen");
			a.erzeugeAusgabe();
		} catch (Exception e) {
			fail();
		}

	}

	@Test
	public void testZip() {
		try {
			ExpandZip a = new ExpandZip(

					new File(".", "target/angular7/src"), new File(".", "src/test/resources/angular7/angular7.zip"));
			a.erzeugeAusgabe();
		} catch (Exception e) {
			fail();
		}

	}

}
