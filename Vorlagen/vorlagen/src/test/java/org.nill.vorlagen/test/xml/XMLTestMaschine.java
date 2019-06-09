package test.xml;

import java.io.File;

import org.nill.vorlagen.xml.st.DocumentSTGenerator;


public class XMLTestMaschine extends DocumentSTGenerator{

	public XMLTestMaschine() throws Exception {
		super(new File(".","src/test/resources/modelle"), 
				new File(".","src/test/resources/vorlagen") ,
				new File("./target/java"),"test.xml.wrap","BeispielWrap");
	}


}
