package org.nill.object;

import java.io.File;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.processing.SupportedAnnotationTypes;
import org.nill.files.FileDazu;
import org.nill.files.ModellAndFile;
import org.nill.files.ModellAndFileErweitern;
import org.nill.generator.Generator;
import org.nill.lists.ListPublisher;
import reactor.core.publisher.Flux;

public class ModellGenerator implements Generator {
	private File vorlagenVerzeichnis;
	private File ausgabeVerzeichnis;
	private List<ObjectModell> modelle;


	public boolean add(ObjectModell e) {
		return modelle.add(e);
	}


	public ModellGenerator(File vorlagenVerzeichnis, File ausgabeVerzeichnis
			) {
		super();
		this.vorlagenVerzeichnis = vorlagenVerzeichnis;
		this.ausgabeVerzeichnis = ausgabeVerzeichnis;
		this.modelle = new ArrayList<ObjectModell>();
	}


	public void erzeugeAusgabe(ObjectModell element) throws Exception {
		element.init();
		Flux.just(element)
		.map(new FileDazu<ObjectModell>(new File(vorlagenVerzeichnis,"single")))
		.flatMap(new ListPublisher<ModellAndFile<ObjectModell>, ModellAndFile<ObjectModell>>(
				new ModellAndFileErweitern<ObjectModell>()))
		.map(new FileDazu<ModellAndFile<ObjectModell>>(ausgabeVerzeichnis))
		.subscribe(new ObjectModellConsumer(StandardCharsets.UTF_8));
	}

	public void setModelle(List<ObjectModell> modelle) {
		this.modelle = modelle;
	}

	@Override
	public void erzeugeAusgabe() throws Exception {
		modelle.forEach(x -> {
			try {
				erzeugeAusgabe(x);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		});
		
	}

}