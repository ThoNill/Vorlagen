package org.nill.modelle.xml;
import java.io.File;

import org.nill.ST.vorlagen.STVorlagenFabrik;
import org.nill.vorlagen.VerzeichnisMaschine;

import nu.xom.Document;



public class XMLVerzeichnisMaschine<VORLAGEN_MODELL> extends  
    VerzeichnisMaschine<Document,VORLAGEN_MODELL> {
		
	
  	public XMLVerzeichnisMaschine(File modellVerzeichnis,
			File vorlageVerzeichnis, STVorlagenFabrik<Document, VORLAGEN_MODELL> vorlagenModelFabrik)
			throws Exception {
		super(modellVerzeichnis, vorlageVerzeichnis,new XMLModelFabrik(),vorlagenModelFabrik);
	}

	


  	

}
