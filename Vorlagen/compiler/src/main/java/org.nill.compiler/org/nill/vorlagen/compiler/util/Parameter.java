package org.nill.vorlagen.compiler.util;

public class Parameter {
	String type;
	String name;
	String annotations;
	
	
	public Parameter(String type, String name, String annotations) {
		super();
		this.type = type;
		this.name = name;
		this.annotations = annotations;
	}
	public String getType() {
		return type;
	}
	public String getName() {
		return name;
	}
	public String getAnnotations() {
		return annotations;
	}
}