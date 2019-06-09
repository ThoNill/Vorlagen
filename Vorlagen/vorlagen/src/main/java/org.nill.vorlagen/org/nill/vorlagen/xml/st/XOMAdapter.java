package org.nill.vorlagen.xml.st;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.stringtemplate.v4.Interpreter;
import org.stringtemplate.v4.ST;
import org.stringtemplate.v4.misc.ObjectModelAdaptor;
import org.stringtemplate.v4.misc.STNoSuchPropertyException;

import nu.xom.Attribute;
import nu.xom.Element;
import nu.xom.Elements;
import nu.xom.Node;

public class XOMAdapter extends ObjectModelAdaptor {
	static Logger logger = Logger.getLogger(XOMAdapter.class.getSimpleName());
	protected String packageName;
	protected String defaultClass;

	public XOMAdapter(String packageName, String defaultClass) {
		super();
		this.packageName = packageName;
		this.defaultClass = defaultClass;
	}

	@Override
	public Object getProperty(Interpreter interpreter, ST self, Object o, Object property, String propertyName)
			throws STNoSuchPropertyException {
		Element elem = null;
		if (o instanceof Element) {
			elem = (Element) o;
		}

		if (propertyName != null) {
			if (propertyName.startsWith("listOf")) {
				return getElements(elem, propertyName.substring(6));
			}
			switch (propertyName) {
			case "Class":
				return o.getClass();
			case "isElement":
				return (o instanceof Element);
			case "isEmptyAttribute":
				if (o instanceof Attribute) {
					return ("emptyAttribute".equals(((Attribute) o).getLocalName()));
				}
				return false;
			case "Childs":
				return getChilds(elem);
			case "Attributes":
				return getAttributes(elem);
			case "Parent":
				return ((Element) o).getParent();
			case "ElementTypeName":
				return ((Element) o).getLocalName();
			case "LocalName":
				if (o instanceof Element) {
					return ((Element) o).getLocalName();
				}
				if (o instanceof Attribute) {
					return ((Attribute) o).getLocalName();
				}
				return "";
			case "Value":
				if (o instanceof Attribute) {
					return ((Attribute) o).getValue();
				}
				return "";
			default:
				return superAufrufen(interpreter, self, property, propertyName, elem);
			}
		}

		return "";
	}

	private Object superAufrufen(Interpreter interpreter, ST self, Object property, String propertyName, Element elem) {
		if (elem != null) {
			WrapElement wrap = getWrap(elem);
			if (wrap != null) {
				return super.getProperty(interpreter, self, wrap, property,

						propertyName);
			}
			propertyName = propertyName.toLowerCase();
			return elem.getAttributeValue(propertyName);
		}
		return "";
	}

	private Object getAttributes(Element elem) {
		List<Attribute> liste = new ArrayList<>();
		for (int i = 0; i < elem.getAttributeCount(); i++) {
			Attribute child = elem.getAttribute(i);
			liste.add(child);
		}
		return liste;
	}

	private Object getElements(Element elem, String name) {
		List<Element> liste = new ArrayList<>();
		getElements(liste, elem, name);
		return liste;
	}

	private Object getChilds(Element elem) {
		List<Node> liste = new ArrayList<>();
		for (int i = 0; i < elem.getChildCount(); i++) {
			Node child = elem.getChild(i);
			liste.add(child);
		}
		return liste;
	}

	private void getElements(List<Element> liste, Element elem, String name) {

		Elements elements = elem.getChildElements();
		for (int i = 0; i < elements.size(); i++) {
			Element child = elements.get(i);
			if (name.equals(child.getLocalName())) {
				liste.add(child);
			} else {
				getElements(liste, child, name);
			}
		}
	}

	private WrapElement getWrap(Element elem) {
		WrapElement wrap = (WrapElement) createInstance(elem.getLocalName(), defaultClass == null);
		if (wrap == null) {
			wrap = (WrapElement) createInstance(defaultClass, false);
			if (wrap == null) {
				return null;
			}
		}
		wrap.setElem(elem);
		return wrap;
	}

	protected Object createInstance(String name, boolean prüfen) {
		if (name != null && packageName != null) {
			String className = packageName + "." + name;
			try {
				Class cl = XOMAdapter.class.getClassLoader().loadClass(className);
				Object obj = cl.newInstance();
				if (prüfen && logger.isLoggable(Level.INFO)) {
					logger.info("Create object of class [" + className + "]");
				}
				return obj;

			} catch (ClassNotFoundException e) {
				if (prüfen) {
					logger.severe("class [" + className + "] not found");
				}
			} catch (InstantiationException e) {
				if (prüfen) {
					logger.severe("class [" + className + "] can not be instantated");
				}
			} catch (IllegalAccessException e) {
				if (prüfen) {
					logger.severe("class [" + className + "] Illegal Access at instantiation");
				}
			}
		}
		return null;
	}

}