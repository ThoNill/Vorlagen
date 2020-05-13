package org.nill.vorlagen.xml.st;

import java.util.ArrayList;
import nu.xom.Document;
import nu.xom.Element;
import nu.xom.Elements;
import java.util.List;
import java.util.function.Function;

public class ListOfElements implements Function<Document,List<Document>> {
	private String elementName;

	public ListOfElements(String elementName) {
		super();
		this.elementName = elementName;
	}

	@Override
	public List<Document> apply(Document document) {
		Element root = document.getRootElement();
		Elements elements = root.getChildElements(elementName);
		List<Document> liste = new ArrayList<>();
		for (int i = 0; i < elements.size(); i++) {
			Element s = elements.get(i);
			Document d = new Document((Element)s.copy());
			liste.add(d);
		}
		return liste;
	}
}
