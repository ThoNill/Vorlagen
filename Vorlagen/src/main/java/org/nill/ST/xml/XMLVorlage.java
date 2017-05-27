package org.nill.ST.xml;

import nu.xom.Document;
import nu.xom.Element;

import org.nill.ST.vorlagen.STVorlage;
import org.stringtemplate.v4.ST;

public class XMLVorlage  extends STVorlage<Document> {

	public XMLVorlage(String dateiName,String packageName) {
		super(dateiName);
		group.registerModelAdaptor(Element.class, new XOMAdapter(packageName));
	}
	
    protected void setzeSTModel(ST t,Document document) {
        super.setzeSTModel(t,document);
        Element root = document.getRootElement();
        t.add("model",root);
    }

}

