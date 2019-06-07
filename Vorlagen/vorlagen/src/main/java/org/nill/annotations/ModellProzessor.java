package org.nill.annotations;

import java.io.File;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.function.Function;

import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.RoundEnvironment;
import javax.annotation.processing.SupportedAnnotationTypes;
import javax.lang.model.element.Element;
import javax.lang.model.element.TypeElement;
import javax.tools.Diagnostic.Kind;

import org.nill.files.FileDazu;
import org.nill.files.ModellAndFile;
import org.nill.files.ModellAndFileErweitern;
import org.nill.lists.ListPublisher;

import reactor.core.publisher.Flux;

@SupportedAnnotationTypes("org.nill.annotations.Modell")
public class ModellProzessor extends AbstractProcessor {
	private File vorlagenVerzeichnisSingle;
	private File vorlagenVerzeichnisMulti;
	private File ausgabeVerzeichnis;
	private Function<TypeElement, TypeElement> toVorlageModell;
	
	public ModellProzessor(File vorlagenVerzeichnisSingle, 
			File vorlagenVerzeichnisMulti, File ausgabeVerzeichnis,
			Function<TypeElement, TypeElement> toVorlageModell) {
		super();
		this.vorlagenVerzeichnisSingle = vorlagenVerzeichnisSingle;
		this.vorlagenVerzeichnisMulti = vorlagenVerzeichnisMulti;
		this.ausgabeVerzeichnis = ausgabeVerzeichnis;
		this.toVorlageModell = toVorlageModell;
	}

	@Override
	public boolean process(Set<? extends TypeElement> annotations, RoundEnvironment roundEnv) {

		processingEnv.getMessager().printMessage(Kind.WARNING, "Prozessor!!");
		System.out.println("Start Prozessor");
		List<TypeElement> modelle = new ArrayList<TypeElement>();
		for (TypeElement annotation : annotations) {
			Set<? extends Element> annotatedElements = roundEnv.getElementsAnnotatedWith(annotation);
			for (Element e : annotatedElements) {
				try {
					if (e instanceof TypeElement) {
						System.out.println("Element abarbeiten");
						modelle.add((TypeElement)e);
						erzeugeAusgabe((TypeElement) e);
					}
				} catch (Exception e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}

		}
		try {
			if(modelle.size()>0) {
				System.out.println("mehrere Elemente abarbeiten");
				erzeugeListAusgabe(modelle);
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println("Ende Prozessor");
		return false;
	}

	public void erzeugeAusgabe(TypeElement element) throws Exception {
		Flux.just(element)
		.map(toVorlageModell)
		.map(new FileDazu<TypeElement>(vorlagenVerzeichnisSingle))
		.flatMap(new ListPublisher<ModellAndFile<TypeElement>, ModellAndFile<TypeElement>>(
				new ModellAndFileErweitern<TypeElement>()))
		.map(new FileDazu<ModellAndFile<TypeElement>>(ausgabeVerzeichnis))
		.subscribe(new AnnotationFileConsumer<TypeElement>(StandardCharsets.UTF_8, processingEnv));
	}

	public void erzeugeListAusgabe(List<TypeElement> elements) throws Exception {
		if(elements.size()==0) {
			return;
		}
		Flux.just(elements)
		.map(new FileDazu<List<TypeElement>>(vorlagenVerzeichnisMulti))
		.flatMap(new ListPublisher<ModellAndFile<List<TypeElement>>, ModellAndFile<List<TypeElement>>>(
				new ModellAndFileErweitern<List<TypeElement>>()))
		.map(new FileDazu<ModellAndFile<List<TypeElement>>>(ausgabeVerzeichnis))
		.subscribe(new ElementListFileConsumer(StandardCharsets.UTF_8, processingEnv));
	}

	
	public void erzeugeAusgabeJava(TypeElement element) throws Exception {
		Flux.just(element)
		.map(toVorlageModell)
		.map(new FileDazu<TypeElement>(vorlagenVerzeichnisSingle))
		.flatMap(new ListPublisher<ModellAndFile<TypeElement>, ModellAndFile<TypeElement>>(
						new ModellAndFileErweitern<TypeElement>()))
		.subscribe(new AnnotationConsumer<TypeElement>(StandardCharsets.UTF_8, processingEnv));
	}
}