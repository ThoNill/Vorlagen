package org.nill.vorlagen.freeform.st;

import java.nio.charset.Charset;

import org.nill.vorlagen.st.STConsumer;
import org.stringtemplate.v4.STGroupFile;
import org.stringtemplate.v4.misc.ObjectModelAdaptor;



public class FreeformSTConsumer<M> extends STConsumer<M> {
		private Class<?> objectClass;
		private ObjectModelAdaptor adapter;

		public FreeformSTConsumer(Charset charset, Class<?> objectClass, ObjectModelAdaptor adapter) {
			super(charset);
			this.objectClass = objectClass;
			this.adapter = adapter;
		}

		@Override
		protected void register(STGroupFile group) {
			super.register(group);
			if(adapter != null) {
				group.registerModelAdaptor(objectClass,adapter);
			}
		}


}