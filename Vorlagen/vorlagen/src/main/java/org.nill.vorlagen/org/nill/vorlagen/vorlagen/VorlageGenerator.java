package org.nill.vorlagen.vorlagen;

import java.io.File;

import org.nill.vorlagen.generator.Generator;
import org.nill.vorlagen.generator.file.FileDazu;
import org.nill.vorlagen.lists.ListPublisher;
import org.nill.vorlagen.lists.ListTransform;

import reactor.core.publisher.Flux;

public class VorlageGenerator<MODELL, MODELL_BESCHREIBUNG, VORLAGEN_MODELL> implements Generator {
	private MODELL_BESCHREIBUNG beschreibung;
	private ListTransform<MODELL_BESCHREIBUNG, MODELL> erzeugeModelle;
	private ListTransform<MODELL, VORLAGEN_MODELL> erzeugeVorlagenModelle;
	private VorlageConsumer<VORLAGEN_MODELL> vorlagenConsumer;
	private File ausgabeVerzeichnis;
	
	public VorlageGenerator(MODELL_BESCHREIBUNG beschreibung, ListTransform<MODELL_BESCHREIBUNG, MODELL> erzeugeModelle,
			ListTransform<MODELL, VORLAGEN_MODELL> erzeugeVorlagenModelle,
			VorlageConsumer<VORLAGEN_MODELL> vorlagenConsumer, File ausgabeVerzeichnis) {
		super();
		this.beschreibung = beschreibung;
		this.erzeugeModelle = erzeugeModelle;
		this.erzeugeVorlagenModelle = erzeugeVorlagenModelle;
		this.vorlagenConsumer = vorlagenConsumer;
		this.ausgabeVerzeichnis = ausgabeVerzeichnis;
	}
	@Override
	public void erzeugeAusgabe() {
		Flux.just(beschreibung)
		.flatMap(new ListPublisher<MODELL_BESCHREIBUNG, MODELL>(erzeugeModelle))
		.flatMap(new ListPublisher<MODELL, VORLAGEN_MODELL>(erzeugeVorlagenModelle))
		.map(new FileDazu<VORLAGEN_MODELL>(ausgabeVerzeichnis)).subscribe(vorlagenConsumer);
	}

}
