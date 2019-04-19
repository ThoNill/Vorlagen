package org.nill.reactive;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.nio.charset.Charset;
import java.util.function.Consumer;

import javax.annotation.processing.ProcessingEnvironment;
import javax.annotation.processing.RoundEnvironment;
import javax.lang.model.element.TypeElement;
import javax.tools.JavaFileObject;

import org.nill.ST.vorlagen.STVorlage;
import org.nill.vorlagen.VorlagenFabrik;
import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;
import org.stringtemplate.v4.STGroupFile;

public class AnnotationConsumer<M extends TypeElement> 
extends STConsumerBasis<M> implements Consumer<ModellAndFile<M>> {
	private ProcessingEnvironment processingEnv;

	public AnnotationConsumer(Charset charset, ProcessingEnvironment processingEnv) {
		super(charset);
		this.processingEnv = processingEnv;
	}

	@Override
	public void accept(ModellAndFile<M> mf) {
		try {
			erzeugeAusgabe(mf.modell, mf.file, null);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	protected void erzeugeAusgabeAusVorlageModell(STGroupFile group, File ausgabeVerzeichnis, M vm)
			throws IOException, FileNotFoundException {
		Writer writer = erzeugeWriter(vm.getQualifiedName().toString());
		erzeugeAusgabe(group, writer, vm);
		writer.close();
	}

	protected Writer erzeugeWriter(String className) throws FileNotFoundException {
		JavaFileObject builderFile;
		try {
			builderFile = processingEnv.getFiler().createSourceFile(className);
			return builderFile.openWriter();
		} catch (IOException e) {
			throw new FileNotFoundException("Die Klasse " + className + " konnte nicht angelegt werden");
		}
	}

	public void setProcessingEnv(ProcessingEnvironment processingEnv) {

	}



}
