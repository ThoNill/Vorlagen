package org.nill.vorlagen.xml.st;

import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.function.Function;

import org.nill.vorlagen.files.DateienEinesVerzeichnisses;
import org.nill.vorlagen.lists.OneElementList;
import org.nill.vorlagen.st.GeneralSTGenerator;
import org.nill.vorlagen.st.STConsumer;
import org.nill.vorlagen.xml.model.File2Document;

import nu.xom.Document;
import reactor.core.publisher.Flux;

public class DocumentSTGenerator extends GeneralSTGenerator<Document> {
	private String modellVerzeichnis;
	private String packageName;
	private String defaultCLass;
	private Function<Document,List<Document>> toVorlageModell;

	public DocumentSTGenerator(String modellVerzeichnis, String vorlagenVerzeichnis, String ausgabeVerzeichnis,
			String packageName, String defaultCLass) {
		this(modellVerzeichnis, vorlagenVerzeichnis, ausgabeVerzeichnis, packageName, defaultCLass,
				new OneElementList<Document>());
	}

	public DocumentSTGenerator(String modellVerzeichnis, String vorlagenVerzeichnis, String ausgabeVerzeichnis,
			String packageName, String defaultCLass, 
			Function<Document,List<Document>> toVorlageModel) {
		super(vorlagenVerzeichnis,ausgabeVerzeichnis);
		this.modellVerzeichnis = modellVerzeichnis;
		this.packageName = packageName;
		this.defaultCLass = defaultCLass;
		this.toVorlageModell = toVorlageModel;
	}

	@Override
	protected STConsumer<Document> createConsumer() {
		return new DocumentSTConsumer(StandardCharsets.UTF_8, packageName, defaultCLass);
	}

	@Override
	protected Flux<Document> createModelFlux() {
		return Flux.just(modellVerzeichnis)
				.flatMapIterable(new DateienEinesVerzeichnisses())
				.map(new File2Document()).flatMapIterable(toVorlageModell);
	}

}
