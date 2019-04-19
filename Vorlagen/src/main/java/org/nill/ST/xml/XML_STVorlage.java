package org.nill.ST.xml;

import java.io.File;
import java.nio.charset.Charset;

import org.nill.ST.vorlagen.STVorlage;
import org.nill.vorlagen.VorlagenFabrik;
import org.stringtemplate.v4.ST;

import nu.xom.Attribute;
import nu.xom.Document;
import nu.xom.Element;

public class XML_STVorlage  extends STVorlage<Document,Document> {


	public XML_STVorlage(
			VorlagenFabrik<Document,Document,File> fabrik,
			Charset charset,String dateiName,File basisVerzeichnis,String packageName,String defaultClass,boolean überschreiben) {
		super(fabrik,charset,dateiName,basisVerzeichnis,überschreiben);
		group.registerModelAdaptor(Element.class, new XOMAdapter(packageName,defaultClass));
		group.registerModelAdaptor(Attribute.class, new XOMAdapter(packageName,defaultClass));
	}
	
    @Override
	protected void setzeSTModel(ST t,Document document) {
        super.setzeSTModel(t,document);
        Element root = document.getRootElement();
        t.add("model",root);
    }

}