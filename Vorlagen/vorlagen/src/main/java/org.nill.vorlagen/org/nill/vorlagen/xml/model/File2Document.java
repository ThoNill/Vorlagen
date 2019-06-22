package org.nill.vorlagen.xml.model;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.function.Function;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.nill.vorlagen.vorlagen.RuntimeVorlagenException;

import nu.xom.Attribute;
import nu.xom.Builder;
import nu.xom.Document;
import nu.xom.Element;
import nu.xom.Elements;
import nu.xom.Node;
import nu.xom.Nodes;
import nu.xom.ParentNode;

public class File2Document implements Function<String, Document> {
	private static final String DESCENDANT = "descendant::*";
	static Logger logger = Logger.getLogger(File2Document.class.getSimpleName());

	@Override
	public Document apply(String file) {
		try {
			logger.log(Level.INFO,"Load: {0}",file);
			InputStream input = holeStream(file);
			logger.log(Level.INFO,"istream: {0}",input);

			Document doc = ladeDasDokument(input);
			logger.log(Level.INFO,"document: {0}",doc);
			return doc;
		} catch (FileNotFoundException e) {
			throw new IllegalStateException(e);
		}
	}

	protected InputStream holeStream(String fileName) throws FileNotFoundException {
		InputStream fileStream;
		if (new File(fileName).exists()) {
			fileStream = new FileInputStream(fileName);
		} else {
			fileStream = Thread.currentThread().getContextClassLoader().getResourceAsStream(fileName);
		}
		return fileStream;
	}

	public static Element ladeDasRootElement(InputStream in) {
		try {
			Document doc = ladeDasDokument(in);
			return doc.getRootElement();
		} catch (Exception ex) {
			throw new RuntimeVorlagenException( "Error at LadeDasRootElement",ex);
		}
	}

	private static Document ladeDasDokument(InputStream in) {
		try {
			XMLElementFabrik factory = new XMLElementFabrik();
			factory.setDir("templates/");
			Builder builder = new Builder(false, factory);

			Document document = builder.build(in);
			Element root = document.getRootElement();
			ersetzeIncludes(root);
			return document;
		} catch (Exception ex) {
			throw new RuntimeVorlagenException( "Error at LadeDasDocument",ex);
		}
	}

	public static void ersetzeElementDurchTemplate(Element source, Element template, String sternValue) {
		ersetzePlacesDurchIntos(source, template);
		int count = source.getAttributeCount();
		for (int i = 0; i < count; i++) {
			Attribute a = source.getAttribute(i);
			ersetzeAlleAttribute(template, a.getLocalName(), a.getValue(), sternValue);
		}
		ersetzeZielDurchDieKinderDerQuelle(source, template);
	}

	private static HashMap<String, Element> extractHashOfElementsWithName(Element source, String name) {
		HashMap<String, Element> childs = new HashMap<>();
		Nodes nodes = source.query(DESCENDANT);
		for (int i = 0; i < nodes.size(); i++) {
			Node c = nodes.get(i);
			if (c instanceof Element && name.equals(((Element) c).getLocalName())) {
				Element e = (Element) c;
				childs.put(e.getAttributeValue("name"), e);
			}
		}
		return childs;
	}

	private static List<Element> extractListOfElements(Element source) {
		List<Element> childs = new ArrayList<>();
		Elements elements = source.getChildElements();
		for (int i = 0; i < elements.size(); i++) {
			childs.add(elements.get(i));
		}
		return childs;
	}

	private static void ersetzePlacesDurchIntos(Element source, Element template) {
		List<Element> intos = extractListOfElements(source);
		HashMap<String, Element> places = extractHashOfElementsWithName(template, "PLACE");
		for (Element into : intos) {
			Element target = places.get(into.getAttributeValue("name"));
			if (target != null) {
				ersetzeZielDurchDieKinderDerQuelle(target, into);
			} else {
				throw new IllegalStateException(
						source.getLocalName() + " hat keinen Platz " + into.getAttributeValue("name"));
			}
		}
	}

	private static void ersetzeAttribut(Attribute attrib, String oldValue, String newValue) {
		if (oldValue.equals(attrib.getValue())) {
			attrib.setValue(newValue);
		}
	}

	private static void ersetzeSternInAttribut(Attribute attrib, String value) {
		String oldValue = attrib.getValue();
		if (oldValue.indexOf('*') >= 0) {
			attrib.setValue(oldValue.replaceAll("\\*", value));
		}
	}

	private static void ersetzeAttributeEinesElements(Element elem, String oldValue, String newValue,
			String sternValue) {
		int count = elem.getAttributeCount();
		for (int i = 0; i < count; i++) {
			Attribute a = elem.getAttribute(i);
			ersetzeAttribut(a, oldValue, newValue);
			ersetzeSternInAttribut(a, sternValue);
		}
	}

	private static void ersetzeAlleAttribute(Element elem, String oldValue, String newValue, String sternValue) {
		ersetzeAttributeEinesElements(elem, oldValue, newValue, sternValue);
		Nodes nodes = elem.query(DESCENDANT);
		for (int i = 0; i < nodes.size(); i++) {
			Node c = nodes.get(i);
			if (c instanceof Element) {
				ersetzeAttributeEinesElements((Element) c, oldValue, newValue, sternValue);
			}
		}
	}

	private static void ersetzeIncludes(Element elem) {
		List<IncludeElement> includes = new ArrayList<>();
		Nodes nodes = elem.query(DESCENDANT);
		for (int i = 0; i < nodes.size(); i++) {
			Node c = nodes.get(i);
			if (c instanceof IncludeElement) {
				includes.add((IncludeElement) c);
			}
		}
		for (IncludeElement c : includes) {
			c.replace();
		}

	}

	private static void ersetzeZielDurchDieKinderDerQuelle(Element target, Element source) {
		ParentNode p = target.getParent();
		int index = p.indexOf(target);
		target.detach();

		List<Element> childs = new ArrayList<>();
		Elements elements = source.getChildElements();
		for (int i = 0; i < elements.size(); i++) {
			childs.add(elements.get(i));
		}

		for (Node c : childs) {
			c.detach();
			p.insertChild(c, index);
			index++;
		}
	}

}
