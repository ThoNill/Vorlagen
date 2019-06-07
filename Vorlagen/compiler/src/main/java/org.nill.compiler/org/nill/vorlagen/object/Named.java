package org.nill.vorlagen.object;

public class Named<K> {
	private String name;
	private K type;

	public Named(String name, K type) {
		super();
		this.name = name;
		this.type = type;
	}

	public String getName() {
		return name;
	}

	public K getType() {
		return type;
	}

	


}
