package org.nill.modelle.xml;

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

	}

	public Element startMakingElement(String name, String namespace) {
		String filename = getDir() + name + ".xml";
		if (IncludeElement.hasTemplate(filename)) {
			return new IncludeElement(name, namespace, filename);
		}
		return new Element(name);
	}

}
