package org.nill.vorlagen.annotations;

import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.function.UnaryOperator;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.RoundEnvironment;
import javax.annotation.processing.SupportedAnnotationTypes;
import javax.lang.model.element.Element;
import javax.lang.model.element.TypeElement;
import javax.tools.Diagnostic.Kind;

import org.nill.vorlagen.generator.file.FileDazu;
import org.nill.vorlagen.generator.file.ModellAndFile;
import org.nill.vorlagen.generator.file.ModellAndFileErweitern;
import org.nill.vorlagen.lists.ListPublisher;
import org.nill.vorlagen.vorlagen.RuntimeVorlagenException;

import reactor.core.publisher.Flux;


@SupportedAnnotationTypes("org.nill.vorlagen.annotations1.Modell")
public class ModellProzessor extends AbstractProcessor {
	static Logger logger = Logger.getLogger(ModellProzessor.class.getSimpleName());

	private String vorlagenVerzeichnisSingle;
	private String vorlagenVerzeichnisMulti;
	private String ausgabeVerzeichnis;
	private UnaryOperator<TypeElement> toVorlageModell;
	
	public ModellProzessor(String vorlagenVerzeichnisSingle, 
			String vorlagenVerzeichnisMulti, String ausgabeVerzeichnis,
			UnaryOperator<TypeElement> toVorlageModell) {
		super();
		this.vorlagenVerzeichnisSingle = vorlagenVerzeichnisSingle;
		this.vorlagenVerzeichnisMulti = vorlagenVerzeichnisMulti;
		this.ausgabeVerzeichnis = ausgabeVerzeichnis;
		this.toVorlageModell = toVorlageModell;
	}

	@Override
	public boolean process(Set<? extends TypeElement> annotations, RoundEnvironment roundEnv) {

		processingEnv.getMessager().printMessage(Kind.WARNING, "Prozessor!!");
		logger.log(Level.INFO, "Start Prozessor");
		List<TypeElement> modelle = new ArrayList<>();
		for (TypeElement annotation : annotations) {
			Set<? extends Element> annotatedElements = roundEnv.getElementsAnnotatedWith(annotation);
			for (Element e : annotatedElements) {
				try {
					if (e instanceof TypeElement) {
						logger.log(Level.INFO, "Element abarbeiten");
						modelle.add((TypeElement)e);
						erzeugeAusgabe((TypeElement) e);
					}
				} catch (Exception e1) {
					throw new RuntimeVorlagenException( "Fehler beim Erzeugen der Ausgabe",e1);
				}
			}

		}
		try {
			if(!modelle.isEmpty()) {
				logger.log(Level.INFO, "mehrere Elemente abarbeiten");
				erzeugeListAusgabe(modelle);
			}
		} catch (Exception e) {
			logger.log(Level.SEVERE, "Fehler beim Erzeugen der erzeugeListAusgabe");
		}
		logger.log(Level.INFO, "Ende Prozessor");
		return false;
	}

	public void erzeugeAusgabe(TypeElement element) {
		Flux.just(element)
		.map(toVorlageModell)
		.map(new FileDazu<TypeElement>(vorlagenVerzeichnisSingle))
		.flatMap(new ListPublisher<ModellAndFile<TypeElement>, ModellAndFile<TypeElement>>(
				new ModellAndFileErweitern<TypeElement>()))
		.map(new FileDazu<ModellAndFile<TypeElement>>(ausgabeVerzeichnis))
		.subscribe(new AnnotationFileConsumer<TypeElement>(StandardCharsets.UTF_8, processingEnv));
	}

	public void erzeugeListAusgabe(List<TypeElement> elements)  {
		if(elements.isEmpty()) {
			return;
		}
		Flux.just(elements)
		.map(new FileDazu<List<TypeElement>>(vorlagenVerzeichnisMulti))
		.flatMap(new ListPublisher<ModellAndFile<List<TypeElement>>, ModellAndFile<List<TypeElement>>>(
				new ModellAndFileErweitern<List<TypeElement>>()))
		.map(new FileDazu<ModellAndFile<List<TypeElement>>>(ausgabeVerzeichnis))
		.subscribe(new ElementListFileConsumer(StandardCharsets.UTF_8, processingEnv));
	}

	
	public void erzeugeAusgabeJava(TypeElement element) {
		Flux.just(element)
		.map(toVorlageModell)
		.map(new FileDazu<TypeElement>(vorlagenVerzeichnisSingle))
		.flatMap(new ListPublisher<ModellAndFile<TypeElement>, ModellAndFile<TypeElement>>(
						new ModellAndFileErweitern<TypeElement>()))
		.subscribe(new AnnotationConsumer<TypeElement>(StandardCharsets.UTF_8, processingEnv));
	}
}