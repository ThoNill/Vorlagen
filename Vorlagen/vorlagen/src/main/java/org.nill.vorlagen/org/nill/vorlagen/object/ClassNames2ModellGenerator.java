package org.nill.vorlagen.object;

import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.logging.Logger;

import org.nill.vorlagen.compiler.ConverterVerzeichnis;
import org.nill.vorlagen.compiler.model.ObjectModell;
import org.nill.vorlagen.compiler.transformation.AnalyseTransformation;
import org.nill.vorlagen.compiler.transformation.GenerateClass;
import org.nill.vorlagen.st.GeneralSTGenerator;
import org.nill.vorlagen.st.STConsumer;

import reactor.core.publisher.Flux;

public class ClassNames2ModellGenerator extends GeneralSTGenerator<ObjectModell> {
	static Logger logger = Logger.getLogger(ClassNames2ModellGenerator.class.getSimpleName());

	private List<String> classNames;
	private List<String> sourcePaths;
	private List<String> optsCompiler;
	private ConverterVerzeichnis converter;
	private ObjectModell verknüpfungen;
	private String modelVerzeichnis;

	public ClassNames2ModellGenerator(String vorlagenVerzeichnis, String ausgabeVerzeichnis, List<String> classNames,
			List<String> sourcePaths, List<String> optsCompiler, ConverterVerzeichnis converter,
			ObjectModell verknüpfungen, String modelVerzeichnis) {
		super(vorlagenVerzeichnis, ausgabeVerzeichnis);
		this.classNames = classNames;
		this.sourcePaths = sourcePaths;
		this.optsCompiler = optsCompiler;
		this.converter = converter;
		this.verknüpfungen = verknüpfungen;
		this.modelVerzeichnis = modelVerzeichnis;
	}


	@Override
	protected STConsumer<ObjectModell> createConsumer() {
		return new ObjectModellConsumer(StandardCharsets.UTF_8);
	}


	@Override
	protected Flux<ObjectModell> createModelFlux() {
		return Flux.fromIterable(classNames).map(new GenerateClass())
				.map(new AnalyseTransformation(converter, verknüpfungen, modelVerzeichnis,sourcePaths,optsCompiler));
	}
}