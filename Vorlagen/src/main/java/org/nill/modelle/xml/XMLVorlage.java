package org.nill.ST.xml;

import nu.xom.Attribute;
import nu.xom.Document;
import nu.xom.Element;

import org.nill.ST.vorlagen.STVorlage;
import org.stringtemplate.v4.ST;

public class XMLVorlage  extends STVorlage<Document> {

	public XMLVorlage(String dateiName,String packageName,String defaultClass) {
		super(dateiName);
		group.registerModelAdaptor(Element.class, new XOMAdapter(packageName,defaultClass));
		group.registerModelAdaptor(Attribute.class, new XOMAdapter(packageName,defaultClass));
	}
	
    protected void setzeSTModel(ST t,Document document) {
        super.setzeSTModel(t,document);
        Element root = document.getRootElement();
        t.add("model",root);
    }

}

