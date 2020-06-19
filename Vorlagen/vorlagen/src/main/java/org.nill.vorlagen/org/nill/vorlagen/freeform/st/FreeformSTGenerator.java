package org.nill.vorlagen.freeform.st;

import java.nio.charset.StandardCharsets;

import org.nill.vorlagen.st.GeneralSTGenerator;
import org.nill.vorlagen.st.STConsumer;
import org.stringtemplate.v4.misc.ObjectModelAdaptor;

import reactor.core.publisher.Flux;

public class FreeformSTGenerator<M> extends GeneralSTGenerator<M>  {

	private Flux<M> modelGenerator;
	private Class<?> objectClass;
	private ObjectModelAdaptor adapter;
	
	public FreeformSTGenerator(Iterable<M> iterable, String vorlagenVerzeichnis, String ausgabeVerzeichnis,
			 Class<?> objectClass, ObjectModelAdaptor adapter) {
		this(Flux.fromIterable(iterable), vorlagenVerzeichnis, ausgabeVerzeichnis, objectClass,adapter);
	}

	public FreeformSTGenerator(Flux<M> modelGenerator, 
			String vorlagenVerzeichnis, String ausgabeVerzeichnis,
			 Class<?> objectClass, ObjectModelAdaptor adapter) {
		super(vorlagenVerzeichnis,ausgabeVerzeichnis);
		this.modelGenerator = modelGenerator;
		this.objectClass = objectClass;
		this.adapter = adapter;
	}


	@Override
	protected STConsumer<M> createConsumer() {
		return new FreeformSTConsumer<>(StandardCharsets.UTF_8, objectClass,adapter);
	}

	@Override
	protected Flux<M> createModelFlux() {
		return modelGenerator;
	}

}
