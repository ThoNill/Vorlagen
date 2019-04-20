package janusAngularj2Frontend;

import java.io.File;
import java.util.function.Function;

import org.nill.reactive.GeneratorList;
import org.nill.reactive.XML_STAusgabe;

import nu.xom.Document;


public class JanusAngular2Maschine extends GeneratorList{

    public JanusAngular2Maschine(
    		File modellVerzeichnis, 
    		File vorlageVerzeichnis) throws Exception {
        super();
        
        add(new XML_STAusgabe(
				modellVerzeichnis, 
				vorlageVerzeichnis,
				new File("./target/angular2/backend"), "test.xml.wrap", "BeispielWrap",new CreateBackendElements()));
        add(new XML_STAusgabe(
				modellVerzeichnis, 
				vorlageVerzeichnis,
				new File("./target/angular2/frontend"), "test.xml.wrap", "BeispielWrap",new CreateFrontendElements())); 
        add(new XML_STAusgabe(
				modellVerzeichnis, 
				vorlageVerzeichnis,
				new File("./target/angular2/page"), "test.xml.wrap", "BeispielWrap"));
    
    }


}
