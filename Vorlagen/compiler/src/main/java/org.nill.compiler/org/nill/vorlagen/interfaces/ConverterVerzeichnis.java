package org.nill.vorlagen.interfaces;

import java.util.HashMap;

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
