package test.xml;

import java.io.File;
import org.nill.reactive.XML_STAusgabe;


public class XMLTestMaschine extends XML_STAusgabe{

	public XMLTestMaschine() throws Exception {
		super(new File(".","src/test/resources/modelle"), 
				new File(".","src/test/resources/vorlagen") ,
				new File("./target/java"),"test.xml.wrap","BeispielWrap");
	}


}
