package org.nill.vorlagen.compiler.transformation;

import java.io.IOException;
import java.util.List;
import java.util.function.Function;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.nill.vorlagen.compiler.Compiler;
import org.nill.vorlagen.compiler.ConverterVerzeichnis;
import org.nill.vorlagen.compiler.model.ObjectModell;
import org.nill.vorlagen.compiler.util.RuntimeCompilerException;

public class AnalyseTransformation implements Function<Class<? extends ObjectModell>, ObjectModell> {
	static Logger logger = Logger.getLogger(AnalyseTransformation.class.getSimpleName());

	private ConverterVerzeichnis converter;
	private ObjectModell verknüpfungen;
	private String modelVerzeichnis;
	private List<String> sourcePaths;
	private List<String> optsCompiler;

	public AnalyseTransformation(ConverterVerzeichnis converter, ObjectModell

	verknüpfungen, String modelVerzeichnis,List<String> sourcePaths,List<String> optsCompiler) {
		super();
		this.converter = converter;
		this.verknüpfungen = verknüpfungen;
		this.modelVerzeichnis = modelVerzeichnis;
		this.sourcePaths = sourcePaths;
		this.optsCompiler = optsCompiler;
	}

	@Override
	public ObjectModell apply(Class<? extends ObjectModell> modellClass) {
		ObjectModell a;
		try {
			logger.log(Level.INFO, "analyse modelVerzeichnis "+modelVerzeichnis);
			a = new Compiler().analyse(modelVerzeichnis, modellClass, converter,sourcePaths,optsCompiler);
			a.addConnection(verknüpfungen);
			return a;
		} catch (IOException | IllegalAccessException e) {
			throw new RuntimeCompilerException(e);
		}
	}

}
