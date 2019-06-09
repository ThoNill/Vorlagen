package org.nill.vorlagen.xml.st;

import java.nio.charset.Charset;

import org.nill.vorlagen.st.STConsumer;
import org.stringtemplate.v4.ST;
import org.stringtemplate.v4.STGroupFile;

import nu.xom.Attribute;
import nu.xom.Document;
import nu.xom.Element;

public class DocumentSTConsumer extends STConsumer<Document> {

	private String packageName;
	private String defaultClass;

	public DocumentSTConsumer(Charset charset, String packageName, String defaultCLass) {
		super(charset);
		this.packageName = packageName;
		this.defaultClass = defaultCLass;
	}

	@Override
	protected void register(STGroupFile group) {
		super.register(group);
		group.registerModelAdaptor(Element.class, new XOMAdapter(packageName, defaultClass));
		group.registerModelAdaptor(Attribute.class, new XOMAdapter(packageName, defaultClass));
	}

    @Override
	protected void setzeSTModel(ST t,Document document) {
		super.setzeSTModel(t,document);
		Element root = document.getRootElement();
	    t.add("root", root);
	}
	
}
