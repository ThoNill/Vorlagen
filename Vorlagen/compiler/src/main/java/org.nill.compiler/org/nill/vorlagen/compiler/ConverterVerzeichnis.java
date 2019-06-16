package org.nill.vorlagen.compiler;

import java.util.HashMap;

import javax.persistence.AttributeConverter;

/**
 * Verwaltet eine Map von Class zu {@link AttributeConverter} Class.
 * 
 * @author tnill
 *
 */
public class ConverterVerzeichnis {
	private HashMap<Class<?>,Class<?>> map = new HashMap<>();
	
	public Class<?> get(Object key) {
		return map.get(key);
	}

	public Class<?> put(Class<?> key, Class<?> value) {
		return map.put(key, value);
	}

	public ConverterVerzeichnis() {
		super();
	}

}
