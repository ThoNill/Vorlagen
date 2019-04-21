package janusAngularj2Frontend;

import java.io.File;
import java.util.function.Function;

import org.nill.ST.xml.DocumentSTGenerator;
import org.nill.generator.GeneratorList;

import nu.xom.Document;


public class JanusAngular2Maschine extends GeneratorList{

    public JanusAngular2Maschine(
    		File modellVerzeichnis, 
    		File vorlageVerzeichnis) throws Exception {
        super();
        
        add(new DocumentSTGenerator(
				modellVerzeichnis, 
				vorlageVerzeichnis,
				new File("./target/angular2/backend"), "test.xml.wrap", "BeispielWrap",new CreateBackendElements()));
        add(new DocumentSTGenerator(
				modellVerzeichnis, 
				vorlageVerzeichnis,
				new File("./target/angular2/frontend"), "test.xml.wrap", "BeispielWrap",new CreateFrontendElements())); 
        add(new DocumentSTGenerator(
				modellVerzeichnis, 
				vorlageVerzeichnis,
				new File("./target/angular2/page"), "test.xml.wrap", "BeispielWrap"));
    
    }


}
