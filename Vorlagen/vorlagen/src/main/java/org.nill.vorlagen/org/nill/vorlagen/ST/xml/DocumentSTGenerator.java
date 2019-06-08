package org.nill.vorlagen.ST.xml;

import java.io.File;
import java.nio.charset.StandardCharsets;
import java.util.function.UnaryOperator;
import org.nill.vorlagen.files.DateienEinesVerzeichnisses;
import org.nill.vorlagen.files.FileDazu;
import org.nill.vorlagen.files.ModellAndFile;
import org.nill.vorlagen.files.ModellAndFileErweitern;
import org.nill.vorlagen.generator.Generator;
import org.nill.vorlagen.lists.ListPublisher;
import org.nill.vorlagen.modelle.xml.File2Document;
import nu.xom.Document;
import reactor.core.publisher.Flux;

public class DocumentSTGenerator implements Generator{
	private File modellVerzeichnis;
	private File vorlagenVerzeichnis;
	private File ausgabeVerzeichnis;
	private String packageName;
	private String defaultCLass;
	private UnaryOperator<Document> toVorlageModell;
	

	public DocumentSTGenerator(File modellVerzeichnis, File vorlagenVerzeichnis, File ausgabeVerzeichnis, String packageName,
			String defaultCLass) {
		this(modellVerzeichnis,vorlagenVerzeichnis,ausgabeVerzeichnis,packageName,defaultCLass,UnaryOperator.identity());
	}
	
	public DocumentSTGenerator(File modellVerzeichnis, File vorlagenVerzeichnis, File ausgabeVerzeichnis, String packageName,
			String defaultCLass,UnaryOperator<Document> toVorlageModel) {
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
		.flatMap(new ListPublisher<File,File>(new DateienEinesVerzeichnisses()))
		.map(new File2Document())
		.map(toVorlageModell)
		.map(new FileDazu<Document>(vorlagenVerzeichnis))
		.flatMap(
				new ListPublisher<ModellAndFile<Document>,ModellAndFile<Document>>(
						new ModellAndFileErweitern<Document>()))
		.map(new FileDazu<ModellAndFile<Document>>(ausgabeVerzeichnis))
		.subscribe(new DocumentSTConsumer(StandardCharsets.UTF_8, packageName, defaultCLass));
	}
	
}
