package org.nill.vorlagen.vorlagen;

import org.nill.vorlagen.generator.Generator;
import org.nill.vorlagen.generator.file.FileDazu;

import java.util.List;
import java.util.function.Function;

import reactor.core.publisher.Flux;

public class VorlageGenerator<MODELL, MODELL_BESCHREIBUNG, VORLAGEN_MODELL> implements Generator {
	private MODELL_BESCHREIBUNG beschreibung;
	private Function<MODELL_BESCHREIBUNG, List<MODELL>> erzeugeModelle;
	private Function<MODELL, List<VORLAGEN_MODELL>> erzeugeVorlagenModelle;
	private VorlageConsumer<VORLAGEN_MODELL> vorlagenConsumer;
	private String ausgabeVerzeichnis;
	
	public VorlageGenerator(MODELL_BESCHREIBUNG beschreibung,
			Function<MODELL_BESCHREIBUNG, List<MODELL>> erzeugeModelle,
			Function<MODELL, List<VORLAGEN_MODELL>> erzeugeVorlagenModelle,
			VorlageConsumer<VORLAGEN_MODELL> vorlagenConsumer, String ausgabeVerzeichnis) {
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
		.flatMapIterable(erzeugeModelle)
		.flatMapIterable(erzeugeVorlagenModelle)
		.map(new FileDazu<VORLAGEN_MODELL>(ausgabeVerzeichnis)).subscribe(vorlagenConsumer);
	}

}
