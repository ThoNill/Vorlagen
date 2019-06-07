package org.nill.vorlagen.object;

import java.lang.reflect.Field;

import javax.persistence.Embeddable;

import org.stringtemplate.v4.Interpreter;
import org.stringtemplate.v4.ST;
import org.stringtemplate.v4.misc.ObjectModelAdaptor;
import org.stringtemplate.v4.misc.STNoSuchPropertyException;


public class ReflectiveFieldAdapter extends ObjectModelAdaptor{

	public ReflectiveFieldAdapter() {
		super();
	}

	public Object getProperty(Interpreter interpreter, ST self, Object o, Object property, String propertyName)
			throws STNoSuchPropertyException {
		if ("embeddable".equals(propertyName)) {
			Field field = (Field)o;
			return field.getType().isAnnotationPresent(Embeddable.class);
		}
		return super.getProperty(interpreter, self,o, property, propertyName);
	}	
}
