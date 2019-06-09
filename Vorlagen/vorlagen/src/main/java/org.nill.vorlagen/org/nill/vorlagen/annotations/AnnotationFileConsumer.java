package org.nill.vorlagen.annotations;

import java.nio.charset.Charset;

import javax.annotation.processing.ProcessingEnvironment;
import javax.lang.model.element.Element;
import javax.lang.model.element.TypeElement;

import org.nill.vorlagen.st.STConsumer;
import org.stringtemplate.v4.STGroupFile;

public class AnnotationFileConsumer<M extends TypeElement> 
extends STConsumer<M>  {
	private ProcessingEnvironment processingEnv;

	public AnnotationFileConsumer(Charset charset, ProcessingEnvironment processingEnv) {
		super(charset);
		this.processingEnv = processingEnv;
	}

		
	@Override
	protected void register(STGroupFile group) {
		group.registerModelAdaptor(Element.class,new ElementAdapter(processingEnv.getElementUtils()));
		super.register(group);
	}



}
