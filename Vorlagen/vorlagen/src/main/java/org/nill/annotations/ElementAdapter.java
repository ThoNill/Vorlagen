package org.nill.annotations;

import javax.lang.model.element.Element;
import javax.lang.model.element.ElementKind;
import javax.lang.model.util.Elements;

import org.stringtemplate.v4.Interpreter;
import org.stringtemplate.v4.ST;
import org.stringtemplate.v4.misc.ObjectModelAdaptor;
import org.stringtemplate.v4.misc.STNoSuchPropertyException;

public class ElementAdapter extends ObjectModelAdaptor {
	private Elements elements;
	
	public ElementAdapter(Elements elements) {
		super();
		this.elements = elements;
	}

	@Override
	public Object getProperty(Interpreter interp, ST self, Object o, Object property, String propertyName)
			throws STNoSuchPropertyException {
		if (o instanceof Element) {
			Element elem = (Element) o;
			switch (propertyName) {
			case "class":
			   return elem.getClass().getCanonicalName();
			case "value":
				return elements.getConstantExpression(elem);
			case "name":
				return elem.getSimpleName().toString();
			case "type":
				return elem.asType();
			case "fields":
				return elem.getEnclosedElements().stream().filter(x -> x.getKind() == ElementKind.FIELD).iterator();
			default:
				break;
			}
		}
		return super.getProperty(interp, self, o, property, propertyName);
	}

}
