package org.nill.vorlagen.xml.st;

import java.nio.charset.StandardCharsets;
import java.util.function.UnaryOperator;

import org.nill.vorlagen.files.DateienEinesVerzeichnisses;
import org.nill.vorlagen.generator.Generator;
import org.nill.vorlagen.generator.file.FileDazu;
import org.nill.vorlagen.generator.file.ModellAndFile;
import org.nill.vorlagen.generator.file.ModellAndFileErweitern;
import org.nill.vorlagen.lists.ListPublisher;
import org.nill.vorlagen.xml.model.File2Document;

import nu.xom.Document;
import reactor.core.publisher.Flux;

public class DocumentSTGenerator implements Generator {
	private String modellVerzeichnis;
	private String vorlagenVerzeichnis;
	private String ausgabeVerzeichnis;
	private String packageName;
	private String defaultCLass;
	private UnaryOperator<Document> toVorlageModell;

	public DocumentSTGenerator(String modellVerzeichnis, String vorlagenVerzeichnis, String ausgabeVerzeichnis,
			String packageName, String defaultCLass) {
		this(modellVerzeichnis, vorlagenVerzeichnis, ausgabeVerzeichnis, packageName, defaultCLass,
				UnaryOperator.identity());
	}

	public DocumentSTGenerator(String modellVerzeichnis, String vorlagenVerzeichnis, String ausgabeVerzeichnis,
			String packageName, String defaultCLass, UnaryOperator<Document> toVorlageModel) {
		super();
		this.modellVerzeichnis = modellVerzeichnis;
		this.vorlagenVerzeichnis = vorlagenVerzeichnis;
		this.ausgabeVerzeichnis = ausgabeVerzeichnis;
		this.packageName = packageName;
		this.defaultCLass = defaultCLass;
		this.toVorlageModell = toVorlageModel;
	}

	@Override
	public void erzeugeAusgabe() {
		Flux.just(modellVerzeichnis)
				.flatMap(new ListPublisher<String, String>(new DateienEinesVerzeichnisses()))
				.map(new File2Document()).map(toVorlageModell).map(new FileDazu<Document>(vorlagenVerzeichnis))
				.flatMap(new ListPublisher<ModellAndFile<Document>, ModellAndFile<Document>>(
						new ModellAndFileErweitern<Document>()))
				.map(new FileDazu<ModellAndFile<Document>>(ausgabeVerzeichnis))
				.subscribe(new DocumentSTConsumer(StandardCharsets.UTF_8, packageName, defaultCLass));
	}

}