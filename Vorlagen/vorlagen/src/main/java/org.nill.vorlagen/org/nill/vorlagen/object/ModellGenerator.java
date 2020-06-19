package org.nill.vorlagen.object;

import java.nio.charset.StandardCharsets;
import java.util.Collection;
import java.util.logging.Logger;

import org.nill.vorlagen.compiler.model.ObjectModell;
import org.nill.vorlagen.st.GeneralSTGenerator;
import org.nill.vorlagen.st.STConsumer;

import reactor.core.publisher.Flux;

public class ModellGenerator extends GeneralSTGenerator<ObjectModell> {
	static Logger logger = Logger.getLogger(ModellGenerator.class.getSimpleName());

	private Collection<ObjectModell> modelle;

	public ModellGenerator(String vorlagenVerzeichnis, String ausgabeVerzeichnis,Collection<ObjectModell> modelle) {
		super(vorlagenVerzeichnis,ausgabeVerzeichnis);
		this.modelle = modelle;
	}

	
	@Override
	protected STConsumer<ObjectModell> createConsumer() {
		return new ObjectModellConsumer(StandardCharsets.UTF_8);
	}


	@Override
	protected Flux<ObjectModell> createModelFlux() {
		return Flux.fromIterable(modelle);
	}
}