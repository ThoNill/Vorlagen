package org.nill.vorlagen.compiler.transformation;

import java.io.IOException;
import java.util.function.Function;

import org.nill.vorlagen.compiler.Compiler;
import org.nill.vorlagen.compiler.ConverterVerzeichnis;
import org.nill.vorlagen.compiler.model.ObjectModell;

public class AnalyseTransformation implements Function<Class<? extends ObjectModell>, ObjectModell> {
	private ConverterVerzeichnis converter;
	private ObjectModell verkn�pfungen;
	private String modelVerzeichnis;

	public AnalyseTransformation(ConverterVerzeichnis converter, ObjectModell

	verkn�pfungen, String modelVerzeichnis) {
		super();
		this.converter = converter;
		this.verkn�pfungen = verkn�pfungen;
		this.modelVerzeichnis = modelVerzeichnis;
	}

	@Override
	public ObjectModell apply(Class<? extends ObjectModell> modellClass) {
		ObjectModell a;
		try {
			System.out.println("analyse modelVerzeichnis "+modelVerzeichnis);
			a = new Compiler().analyse(modelVerzeichnis, modellClass, converter);
			a.addConnection(verkn�pfungen);
			return a;
		} catch (IOException | IllegalAccessException e) {
			throw new RuntimeException(e);
		}
	}

}
