package org.nill.modelle.xml;

import nu.xom.Document;
import nu.xom.Element;

import org.nill.ST.STVorlage;
import org.stringtemplate.v4.STGroup;

public class AdapterGroup  extends STVorlage<Document> {

	public AdapterGroup(String dateiName,String packageName) {
		super(dateiName);
		group.registerModelAdaptor(Element.class, new XOMAdapter(packageName));
	}
}

