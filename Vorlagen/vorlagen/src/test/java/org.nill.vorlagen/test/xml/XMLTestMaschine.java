package test.xml;

import java.io.File;

import org.nill.vorlagen.xml.st.DocumentSTGenerator;


public class XMLTestMaschine extends DocumentSTGenerator{

	public XMLTestMaschine() throws Exception {
		super("modelle", 
				"javavorlagen",
				"./target/java","test.xml.wrap","BeispielWrap",XMLTestMaschine.class);
	}


}
