package org.nill.ST.xml;

import nu.xom.Document;
import nu.xom.Element;

import org.nill.ST.vorlagen.STVorlage;

public class XMLVorlage  extends STVorlage<Document> {

	public XMLVorlage(String dateiName,String packageName) {
		super(dateiName);
		group.registerModelAdaptor(Element.class, new XOMAdapter(packageName));
	}
}

