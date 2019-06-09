package org.nill.vorlagen.annotations;

import java.nio.charset.Charset;
import java.util.List;

import javax.annotation.processing.ProcessingEnvironment;
import javax.lang.model.element.Element;
import javax.lang.model.element.TypeElement;

import org.nill.vorlagen.st.STConsumer;
import org.stringtemplate.v4.STGroupFile;

public class ElementListFileConsumer 
extends STConsumer<List<TypeElement>>  {
	private ProcessingEnvironment processingEnv;

	public ElementListFileConsumer(Charset charset, ProcessingEnvironment processingEnv) {
		super(charset);
		this.processingEnv = processingEnv;
	}

		
	@Override
	protected void register(STGroupFile group) {
		super.register(group);
		group.registerModelAdaptor(Element.class,new ElementAdapter(processingEnv.getElementUtils()));
	}





}
