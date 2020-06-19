package janusAngularj2Frontend;

import org.nill.vorlagen.generator.GeneratorList;
import org.nill.vorlagen.xml.st.DocumentSTGenerator;


public class JanusAngular2Maschine extends GeneratorList{

    public JanusAngular2Maschine(
    		String modellVerzeichnis, 
    		String vorlageVerzeichnis) throws Exception {
        super();
        
        add(new DocumentSTGenerator(
				modellVerzeichnis, 
				vorlageVerzeichnis,
				"./target/angular2/backend", 
				"test.xml.wrap", "BeispielWrap"));

    }


}
