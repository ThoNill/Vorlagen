package org.nill.vorlagen.compiler.transformation;

import java.util.function.Function;

import javax.management.RuntimeErrorException;

import org.nill.vorlagen.compiler.model.ObjectModell;

public class GenerateClass implements Function<String, Class<? extends ObjectModell>> {

	public GenerateClass() {
		super();
	}

	@Override
	public Class<? extends ObjectModell> apply(String className) {
		try {
			System.out.println("Erzeuge class "+className);
			Class<?> clazz = Thread.currentThread().getContextClassLoader().loadClass(className);
			if (ObjectModell.class.isAssignableFrom(clazz)) {
				System.out.println("Habe class "+className + " erzeugt");
				return (Class<? extends ObjectModell>)clazz;
			} else {
				throw new RuntimeException("The Class " + clazz.getCanonicalName() + " isnt a ObjectModell");
			}
		} catch (ClassNotFoundException e) {
			throw new RuntimeException("class " + className,e);
		}
	}

}
