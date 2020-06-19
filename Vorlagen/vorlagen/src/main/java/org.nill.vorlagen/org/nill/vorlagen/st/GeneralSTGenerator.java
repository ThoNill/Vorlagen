package org.nill.vorlagen.st;

import org.nill.vorlagen.generator.Generator;
import org.nill.vorlagen.generator.file.FileDazu;
import org.nill.vorlagen.generator.file.ModellAndFile;
import org.nill.vorlagen.generator.file.ModellAndFileErweitern;

import reactor.core.publisher.Flux;

public abstract class GeneralSTGenerator<M> implements Generator  {

	private String vorlagenVerzeichnis;
	private String ausgabeVerzeichnis;
	
	

	public GeneralSTGenerator(
			String vorlagenVerzeichnis, String ausgabeVerzeichnis) {
		super();
		this.vorlagenVerzeichnis = vorlagenVerzeichnis;
		this.ausgabeVerzeichnis = ausgabeVerzeichnis;
	}

	@Override
	public void erzeugeAusgabe() {
		createModelFlux()
				.map(new FileDazu<M>(vorlagenVerzeichnis))
				.flatMapIterable(new ModellAndFileErweitern<M>())
				.map(new FileDazu<ModellAndFile<M>>(ausgabeVerzeichnis))
				.subscribe(createConsumer());
	}
	
	protected abstract STConsumer<M> createConsumer();
	protected abstract Flux<M> createModelFlux();
}
