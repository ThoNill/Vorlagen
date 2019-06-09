package org.nill.vorlagen.xml.model;

import nu.xom.Element;
import nu.xom.NodeFactory;

public class XMLElementFabrik extends NodeFactory {
	private String dir;

	public String getDir() {
		return dir;
	}

	public void setDir(String dir) {
		this.dir = dir;
	}

	public XMLElementFabrik() {
		super();
	}

	@Override
    public Element startMakingElement(String name, String namespace) {
		String filename = getDir() + name + ".xml";
		if (IncludeElement.hasTemplate(filename)) {
			return new IncludeElement(name, namespace, filename);
		}
		return new Element(name);
	}

}
