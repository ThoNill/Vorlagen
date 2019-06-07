package janusAngularj2Frontend;

import java.io.File;

import org.nill.ST.xml.DocumentSTGenerator;
import org.nill.generator.GeneratorList;


public class JanusAngular2Maschine extends GeneratorList{

    public JanusAngular2Maschine(
    		File modellVerzeichnis, 
    		File vorlageVerzeichnis) throws Exception {
        super();
        
        add(new DocumentSTGenerator(
				modellVerzeichnis, 
				new File(vorlageVerzeichnis,"JanusAngular2Backend.stg"),
				new File("./target/angular2/backend"), "test.xml.wrap", "BeispielWrap",new CreateBackendElements()));
        add(new DocumentSTGenerator(
    				modellVerzeichnis, 
    				new File(vorlageVerzeichnis,"repositorysBackend.stg"),
    				new File("./target/angular2/backend"), "test.xml.wrap", "BeispielWrap",new CreateBackendElements()));
        add(new DocumentSTGenerator(
    				modellVerzeichnis, 
    				new File(vorlageVerzeichnis,"JanusAngular2RestController.stg"),
    				new File("./target/angular2/backend"), "test.xml.wrap", "BeispielWrap",new CreateBackendElements()));
        
        add(new DocumentSTGenerator(
				modellVerzeichnis, 
				new File(vorlageVerzeichnis,"JanusAngular2Frontend.stg"),
				new File("./target/angular2/frontend"), "test.xml.wrap", "BeispielWrap",new CreateFrontendElements())); 
        add(new DocumentSTGenerator(
				modellVerzeichnis, 
				new File(vorlageVerzeichnis,"JanusAngular2Page.stg"),
				new File("./target/angular2/page"), "test.xml.wrap", "BeispielWrap"));
    
    }


}
