package org.nill.vorlagen.xml.st;

import java.io.File;
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

public class DocumentSTGenerator implements Generator{
	private String modellVerzeichnis;
	private String vorlagenVerzeichnis;
	private String ausgabeVerzeichnis;
	private String packageName;
	private String defaultCLass;
	private UnaryOperator<Document> toVorlageModell;
	private Class<?> ankerClass;

	public DocumentSTGenerator(String modellVerzeichnis, 
			String vorlagenVerzeichnis, 
			String ausgabeVerzeichnis, 
			String packageName,
			String defaultCLass,
			Class<?> ankerClass) {
		this(modellVerzeichnis,vorlagenVerzeichnis,ausgabeVerzeichnis,packageName,defaultCLass,UnaryOperator.identity(),ankerClass);
	}
	
	public DocumentSTGenerator(
			String modellVerzeichnis, 
			String vorlagenVerzeichnis, 
			String ausgabeVerzeichnis, 
			String packageName,
			String defaultCLass,
			UnaryOperator<Document> toVorlageModel,
			Class<?> ankerClass) {
		super();
		this.modellVerzeichnis = modellVerzeichnis;
		this.vorlagenVerzeichnis = vorlagenVerzeichnis;
		this.ausgabeVerzeichnis = ausgabeVerzeichnis;
		this.packageName = packageName;
		this.defaultCLass = defaultCLass;
		this.toVorlageModell = toVorlageModel;
		this.ankerClass = ankerClass;
	}


	@Override
	public void erzeugeAusgabe() {
		Flux.just(modellVerzeichnis)
		.flatMap(new ListPublisher<String,String>(new DateienEinesVerzeichnisses(ankerClass)))
		.map(new File2Document())
		.map(toVorlageModell)
		.map(new FileDazu<Document>(vorlagenVerzeichnis))
		.flatMap(
				new ListPublisher<ModellAndFile<Document>,ModellAndFile<Document>>(
						new ModellAndFileErweitern<Document>(ankerClass)))
		.map(new FileDazu<ModellAndFile<Document>>(ausgabeVerzeichnis))
		.subscribe(new DocumentSTConsumer(StandardCharsets.UTF_8, packageName, defaultCLass));
	}
	
}
