package org.nill.object;

import org.stringtemplate.v4.Interpreter;
import org.stringtemplate.v4.ST;
import org.stringtemplate.v4.misc.MapModelAdaptor;
import org.stringtemplate.v4.misc.STNoSuchPropertyException;


public class ObjectModellAdapter extends MapModelAdaptor {

	public ObjectModellAdapter() {
		super();
	}

	@Override
	public Object getProperty(Interpreter interpreter, ST self, Object o, Object property, String propertyName)
			throws STNoSuchPropertyException {
		if ("class".equals(propertyName)) {
			return o.getClass();
		}
		if (o instanceof ObjectModell && propertyName.startsWith("list")) {
			return ((ObjectModell)o).getFieldValuesOfKind(
					propertyName.substring("list".length())
					);
		}
		return super.getProperty(interpreter, self, o, property, propertyName);
	}	
}
