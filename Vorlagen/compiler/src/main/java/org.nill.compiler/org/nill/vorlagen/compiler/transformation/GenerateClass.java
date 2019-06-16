package org.nill.vorlagen.compiler.transformation;

import java.util.function.Function;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.nill.vorlagen.compiler.model.ObjectModell;

public class GenerateClass implements Function<String, Class<? extends ObjectModell>> {
	static Logger logger = Logger.getLogger(GenerateClass.class.getSimpleName());

	public GenerateClass() {
		super();
	}

	@Override
	public Class<? extends ObjectModell> apply(String className) {
		try {
			logger.log(Level.INFO,"Erzeuge class "+className);
			Class<?> clazz = Thread.currentThread().getContextClassLoader().loadClass(className);
			if (ObjectModell.class.isAssignableFrom(clazz)) {
				logger.log(Level.INFO,"Habe class "+className + " erzeugt");
				return (Class<? extends ObjectModell>)clazz;
			} else {
				throw new RuntimeException("The Class " + clazz.getCanonicalName() + " isnt a ObjectModell");
			}
		} catch (ClassNotFoundException e) {
			throw new RuntimeException("class " + className,e);
		}
	}

}
