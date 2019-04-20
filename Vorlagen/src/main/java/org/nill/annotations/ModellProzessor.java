package org.nill.annotations;

import java.io.File;
import java.nio.charset.StandardCharsets;
import java.util.Set;
import java.util.function.Function;

import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.RoundEnvironment;
import javax.annotation.processing.SupportedAnnotationTypes;
import javax.lang.model.element.Element;
import javax.lang.model.element.TypeElement;
import javax.tools.Diagnostic.Kind;

import org.nill.reactive.FileDazu;
import org.nill.reactive.ListPublisher;
import org.nill.reactive.ModelAndFileErweitern;
import org.nill.reactive.ModellAndFile;
import org.nill.reactive.STConsumer;

import reactor.core.publisher.Flux;

@SupportedAnnotationTypes("org.nill.annotations.Modell")
public class ModellProzessor extends AbstractProcessor {
	private File vorlagenVerzeichnis;
	private File ausgabeVerzeichnis;
	private Function<Element,Element> toVorlageModell;
	
	public ModellProzessor() {
		this(new File("src/main/resources/javavorlagen"),new File("./target/javatest"));
	}

	public ModellProzessor( File vorlagenVerzeichnis, File ausgabeVerzeichnis ) {
		this(vorlagenVerzeichnis,ausgabeVerzeichnis,Function.identity());
	}
	
	public ModellProzessor(File vorlagenVerzeichnis, File ausgabeVerzeichnis,Function<Element,Element> toVorlageModel) {
		super();
		this.vorlagenVerzeichnis = vorlagenVerzeichnis;
		this.ausgabeVerzeichnis = ausgabeVerzeichnis;
		this.toVorlageModell = toVorlageModel;
	}


	@Override
	public boolean process(Set<? extends TypeElement> annotations, RoundEnvironment roundEnv) {
		 processingEnv.getMessager().printMessage(Kind.NOTE,"Da!!");
		 for (TypeElement annotation : annotations) {
		        Set<? extends Element> annotatedElements 
		          = roundEnv.getElementsAnnotatedWith(annotation);
		        for (Element e : annotatedElements) {
		        	try {
						erzeugeAusgabe(e);
					} catch (Exception e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
		        }
		      
		    }
		return false;
	}
	
	  
  	public void erzeugeAusgabe(Element element) throws Exception {
  		System.out.println(element.getSimpleName());
  		Flux.just(element)
  		.map(toVorlageModell)
  		.map(new FileDazu<Element>(vorlagenVerzeichnis))
  		.flatMap(
  				new ListPublisher<ModellAndFile<Element>,ModellAndFile<Element>>(
  						new ModelAndFileErweitern<Element>()))
  		.map(new FileDazu<ModellAndFile<Element>>(ausgabeVerzeichnis))
  		.subscribe(new STConsumer<Element>(StandardCharsets.UTF_8));
  	}
}