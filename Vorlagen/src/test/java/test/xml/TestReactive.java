package test.xml;

import static org.junit.Assert.fail;

import java.io.File;

import org.junit.Test;
import org.nill.reactive.XML_STAusgabe;

public class TestReactive {

	@Test
	public void test() {

		XML_STAusgabe a = new XML_STAusgabe(

				new File(".", "src/test/resources/modelle"), new File(".", "src/test/resources/vorlagen"),
				new File("./target/reactive"), "test.xml.wrap", "BeispielWrap");
		try {
			a.erzeugeAusgabe();
		} catch (Exception e) {
			fail();
		}

	}

}
