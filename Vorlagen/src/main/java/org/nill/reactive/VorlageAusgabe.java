package org.nill.reactive;

import java.io.File;

import reactor.core.publisher.Flux;

public class VorlageAusgabe<MODELL, MODELL_BESCHREIBUNG, VORLAGEN_MODELL> implements Generator {
	private MODELL_BESCHREIBUNG beschreibung;
	private ListTransform<MODELL_BESCHREIBUNG, MODELL> erzeugeModelle;
	private ListTransform<MODELL, VORLAGEN_MODELL> erzeugeVorlagenModelle;
	private VorlageConsumer<VORLAGEN_MODELL> vorlagenConsumer;
	private File ausgabeVerzeichnis;
	
	public VorlageAusgabe(MODELL_BESCHREIBUNG beschreibung, ListTransform<MODELL_BESCHREIBUNG, MODELL> erzeugeModelle,
			ListTransform<MODELL, VORLAGEN_MODELL> erzeugeVorlagenModelle,
			VorlageConsumer<VORLAGEN_MODELL> vorlagenConsumer, File ausgabeVerzeichnis) {
		super();
		this.beschreibung = beschreibung;
		this.erzeugeModelle = erzeugeModelle;
		this.erzeugeVorlagenModelle = erzeugeVorlagenModelle;
		this.vorlagenConsumer = vorlagenConsumer;
		this.ausgabeVerzeichnis = ausgabeVerzeichnis;
	}
	public void erzeugeAusgabe() throws Exception {
		Flux.just(beschreibung)
		.flatMap(new ListPublisher<MODELL_BESCHREIBUNG, MODELL>(erzeugeModelle))
		.flatMap(new ListPublisher<MODELL, VORLAGEN_MODELL>(erzeugeVorlagenModelle))
		.map(new FileDazu<VORLAGEN_MODELL>(ausgabeVerzeichnis)).subscribe(vorlagenConsumer);
	}

}
