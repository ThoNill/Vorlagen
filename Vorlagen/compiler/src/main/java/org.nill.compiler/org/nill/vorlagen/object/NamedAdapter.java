package org.nill.vorlagen.object;

import org.stringtemplate.v4.Interpreter;
import org.stringtemplate.v4.ST;
import org.stringtemplate.v4.misc.ObjectModelAdaptor;
import org.stringtemplate.v4.misc.STNoSuchPropertyException;


public class NamedAdapter extends ObjectModelAdaptor{

	public NamedAdapter() {
		super();
	}

	public Object getProperty(Interpreter interpreter, ST self, Object o, Object property, String propertyName)
			throws STNoSuchPropertyException {
		if ("class".equals(propertyName)) {
			return o.getClass();
		}
		if ("name".equals(propertyName)) {
			return ((Named)o).getName();
		}
		return super.getProperty(interpreter, self, ((Named)o).getType(), property, propertyName);
	}	
}
