package org.nill.vorlagen.object;

import java.lang.reflect.Field;
import java.nio.charset.Charset;

import org.nill.vorlagen.compiler.model.ObjectModell;
import org.nill.vorlagen.st.STConsumer;
import org.stringtemplate.v4.STGroupFile;

public class ObjectModellConsumer extends STConsumer<ObjectModell>{

	public ObjectModellConsumer(Charset charSet) {
		super(charSet);
	}

	@Override
	protected void register(STGroupFile group) {
		super.register(group);
		group.registerModelAdaptor(Field.class,new ReflectiveFieldAdapter());
		group.registerModelAdaptor(ObjectModell.class,new ObjectModellAdapter());
	}
}
