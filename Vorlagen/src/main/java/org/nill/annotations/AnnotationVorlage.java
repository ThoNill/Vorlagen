package org.nill.annotations;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.nio.charset.Charset;

import javax.annotation.processing.ProcessingEnvironment;
import javax.annotation.processing.RoundEnvironment;
import javax.tools.JavaFileObject;

import org.nill.ST.vorlagen.STVorlage;
import org.nill.vorlagen.VorlagenFabrik;

public class AnnotationVorlage<MODELL, VORLAGEN_MODELL> extends STVorlage<MODELL, VORLAGEN_MODELL> {
	private ProcessingEnvironment processingEnv;

	public AnnotationVorlage(VorlagenFabrik<MODELL, VORLAGEN_MODELL, File> vorlagenFabrik, Charset charset,
			String dateiName, File basisVerzeichnis) {
		super(vorlagenFabrik, charset, dateiName, basisVerzeichnis, true);
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
		this.processingEnv = processingEnv;
	}

}
