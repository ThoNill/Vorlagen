package org.nill.vorlagen.object;

import java.io.File;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.nill.vorlagen.compiler.ConverterVerzeichnis;
import org.nill.vorlagen.compiler.model.ObjectModell;
import org.nill.vorlagen.compiler.transformation.AnalyseTransformation;
import org.nill.vorlagen.compiler.transformation.GenerateClass;
import org.nill.vorlagen.generator.Generator;
import org.nill.vorlagen.generator.file.FileDazu;
import org.nill.vorlagen.generator.file.ModellAndFile;
import org.nill.vorlagen.generator.file.ModellAndFileErweitern;
import org.nill.vorlagen.lists.ListPublisher;

import reactor.core.publisher.Flux;

public class ModellGenerator implements Generator {
	static Logger logger = Logger.getLogger(ModellGenerator.class.getSimpleName());

	private String vorlagenVerzeichnis;
	private String ausgabeVerzeichnis;
	private List<ObjectModell> modelle;
	private Class ankerClass;

	public boolean add(ObjectModell e) {
		return modelle.add(e);
	}

	public ModellGenerator(String vorlagenVerzeichnis, String ausgabeVerzeichnis, Class ankerClass) {
		super();
		this.vorlagenVerzeichnis = vorlagenVerzeichnis;
		this.ausgabeVerzeichnis = ausgabeVerzeichnis;
		this.modelle = new ArrayList<>();
		this.ankerClass = ankerClass;
	}

	public void erzeugeAusgabe(ObjectModell element) {
		erzeugeAusgabe(Flux.just(element));
	}

	public void erzeugeAusgabe(Flux<ObjectModell> flux) {
		flux.map(new FileDazu<ObjectModell>(vorlagenVerzeichnis + "/single"))
				.flatMap(new ListPublisher<ModellAndFile<ObjectModell>, ModellAndFile<ObjectModell>>(
						new ModellAndFileErweitern<ObjectModell>(ankerClass)))
				.map(new FileDazu<ModellAndFile<ObjectModell>>(ausgabeVerzeichnis))
				.subscribe(new ObjectModellConsumer(StandardCharsets.UTF_8));
	}

	public void setModelle(List<ObjectModell> modelle) {
		this.modelle = modelle;
	}

	@Override
	public void erzeugeAusgabe() {
		modelle.forEach(x -> {
			try {
				erzeugeAusgabe(x);
			} catch (Exception e) {
				logger.log(Level.SEVERE, "Fehler in  erzeugeAusgabe");
			}
		});

	}

	public void erzeugeAusgabe(String className, ConverterVerzeichnis converter, ObjectModell verknüpfungen,
			String modelVerzeichnis) {
		erzeugeAusgabe(Flux.just(className).map(new GenerateClass())
				.map(new AnalyseTransformation(converter, verknüpfungen, modelVerzeichnis)));
	}
}