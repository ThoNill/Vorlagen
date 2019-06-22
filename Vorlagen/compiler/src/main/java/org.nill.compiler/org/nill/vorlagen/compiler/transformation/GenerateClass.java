package org.nill.vorlagen.compiler.transformation;

import java.util.function.Function;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.nill.vorlagen.compiler.model.ObjectModell;
import org.nill.vorlagen.compiler.util.RuntimeCompilerException;

public class GenerateClass implements Function<String, Class<? extends ObjectModell>> {
	static Logger logger = Logger.getLogger(GenerateClass.class.getSimpleName());

	public GenerateClass() {
		super();
	}

	@Override
	public Class<? extends ObjectModell> apply(String className) {
		try {
			logger.log(Level.INFO,"Erzeuge class {0}",className);
			Class<?> clazz = Thread.currentThread().getContextClassLoader().loadClass(className);
			if (ObjectModell.class.isAssignableFrom(clazz)) {
				logger.log(Level.INFO,"Habe class {0} erzeugt",className);
				return (Class<? extends ObjectModell>)clazz;
			} else {
				throw new RuntimeCompilerException("The Class " + clazz.getCanonicalName() + " isnt a ObjectModell");
			}
		} catch (ClassNotFoundException e) {
			throw new RuntimeCompilerException("class not found " + className,e);
		}
	}

}
