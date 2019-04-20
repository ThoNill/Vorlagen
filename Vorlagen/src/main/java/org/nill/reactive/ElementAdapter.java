package org.nill.reactive;

import javax.lang.model.element.Element;
import javax.lang.model.element.ElementKind;

import org.stringtemplate.v4.Interpreter;
import org.stringtemplate.v4.ST;
import org.stringtemplate.v4.misc.ObjectModelAdaptor;
import org.stringtemplate.v4.misc.STNoSuchPropertyException;

public class ElementAdapter extends ObjectModelAdaptor {

	@Override
	public Object getProperty(Interpreter interp, ST self, Object o, Object property, String propertyName)
			throws STNoSuchPropertyException {
		if (o instanceof Element) {
			Element elem = (Element) o;
			switch (propertyName) {
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
