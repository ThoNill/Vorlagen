package org.nill.object;

import java.nio.charset.Charset;

import org.nill.reactive.STConsumer;
import org.stringtemplate.v4.STGroupFile;

public class ObjectModellConsumer extends STConsumer<ObjectModell>{

	public ObjectModellConsumer(Charset charSet) {
		super(charSet);
	}

	@Override
	protected void register(STGroupFile group) {
		super.register(group);
		group.registerModelAdaptor(Named.class,new NamedAdapter());

		group.registerModelAdaptor(ObjectModell.class,new ObjectModellAdapter());
	}
}
