package org.nill.ST.xml;

import java.io.File;
import java.util.function.Function;
import org.nill.modelle.xml.XMLVerzeichnisMaschine;
import nu.xom.Document;

public class XML_STVerzeichnisMaschine extends XMLVerzeichnisMaschine<Document> {

	public XML_STVerzeichnisMaschine(File modellVerzeichnis, File vorlageVerzeichnis,
			File basisAusgabeVerzeichnis,String packageName, String defaultClass,boolean überschreiben,Function<Document,Document> transformation) throws Exception {
		super(modellVerzeichnis, vorlageVerzeichnis,new XML_STVorlagenFabrik(vorlageVerzeichnis,basisAusgabeVerzeichnis, packageName, defaultClass,überschreiben,transformation));
	}
	
	public XML_STVerzeichnisMaschine(File modellVerzeichnis, File vorlageVerzeichnis,
			File basisAusgabeVerzeichnis,String packageName, String defaultClass) throws Exception {
		this(modellVerzeichnis,vorlageVerzeichnis,basisAusgabeVerzeichnis,packageName,defaultClass,true,Function.identity());
	}

}
