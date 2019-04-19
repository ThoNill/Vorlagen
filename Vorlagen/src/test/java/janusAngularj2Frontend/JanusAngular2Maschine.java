package janusAngularj2Frontend;

import java.io.File;
import java.util.function.Function;

import org.nill.ST.xml.XML_STVerzeichnisMaschine;
import org.nill.vorlagen.GeneratorList;
import nu.xom.Document;


public class JanusAngular2Maschine extends GeneratorList{

    public JanusAngular2Maschine(
    		File modellVerzeichnis, 
    		File vorlageVerzeichnis) throws Exception {
        super();
        add( new XML_STVerzeichnisMaschine(   
                modellVerzeichnis, vorlageVerzeichnis,
    			new File("./target/angular2/backend"),"","", true,
    			new CreateBackendElements()));
        add( new XML_STVerzeichnisMaschine(   
                modellVerzeichnis, vorlageVerzeichnis,
                new File("./target/angular2/frontend"),"","", true,
    			new CreateFrontendElements()));
        add( new XML_STVerzeichnisMaschine(   
                modellVerzeichnis, vorlageVerzeichnis,
                new File("./target/angular2/page"),"","", true, 
                Function.identity()));
      
        //new BackendXMLDefinition("repositorysBackend", "modelle", "vorlagen","java",null,null,this,StandardCharsets.UTF_8);
        /*
        new BackendXMLDefinition("JanusAngular2RestController", "modelle", "vorlagen","java","janusAngular2Backend.wraps",null,this,StandardCharsets.UTF_8);
        new BackendXMLDefinition("JanusAngular2Backend", "modelle", "vorlagen","java",null,null,this,StandardCharsets.UTF_8);
        new FrontendXMLDefinition("JanusAngular2Frontend", "modelle", "vorlagen","ts",null,null,this,StandardCharsets.UTF_8);
        new FrontendPageComponentDefinition("JanusAngular2Page", "modelle", "vorlagen","ts",null,null,this,StandardCharsets.UTF_8);
        */
    }


}
