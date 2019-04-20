package org.nill.reactive;

import java.io.File;
import java.nio.charset.StandardCharsets;
import java.util.function.Function;

import org.nill.modelle.xml.File2XML;

import nu.xom.Document;
import reactor.core.publisher.Flux;

public class XML_STAusgabe implements Generator{
	private File modellVerzeichnis;
	private File vorlagenVerzeichnis;
	private File ausgabeVerzeichnis;
	private String packageName;
	private String defaultCLass;
	private Function<Document,Document> toVorlageModell;
	

	public XML_STAusgabe(File modellVerzeichnis, File vorlagenVerzeichnis, File ausgabeVerzeichnis, String packageName,
			String defaultCLass) {
		this(modellVerzeichnis,vorlagenVerzeichnis,ausgabeVerzeichnis,packageName,defaultCLass,Function.identity());
	}
	
	public XML_STAusgabe(File modellVerzeichnis, File vorlagenVerzeichnis, File ausgabeVerzeichnis, String packageName,
			String defaultCLass,Function<Document,Document> toVorlageModel) {
		super();
		this.modellVerzeichnis = modellVerzeichnis;
		this.vorlagenVerzeichnis = vorlagenVerzeichnis;
		this.ausgabeVerzeichnis = ausgabeVerzeichnis;
		this.packageName = packageName;
		this.defaultCLass = defaultCLass;
		this.toVorlageModell = toVorlageModel;
	}


	@Override
	public void erzeugeAusgabe() throws Exception {
		Flux.just(modellVerzeichnis)
		.flatMap(new ListPublisher<File,File>(new DateienEinesVerzeichnisses()))
		.map(new File2XML())
		.map(toVorlageModell)
		.map(new FileDazu<Document>(vorlagenVerzeichnis))
		.flatMap(
				new ListPublisher<ModellAndFile<Document>,ModellAndFile<Document>>(
						new ModelAndFileErweitern<Document>()))
		.map(new FileDazu<ModellAndFile<Document>>(ausgabeVerzeichnis))
		.subscribe(new XML_STConsumer(StandardCharsets.UTF_8, packageName, defaultCLass));
	}
	
}
