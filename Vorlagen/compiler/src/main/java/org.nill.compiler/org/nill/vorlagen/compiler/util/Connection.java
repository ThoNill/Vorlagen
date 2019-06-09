package org.nill.vorlagen.compiler.util;

public class Connection extends WithKind {
	private Class<?> source;
	private Class<?> target;

	public Connection(String kind,Class<?> source, Class<?> target) {
		super(kind);
		this.source = source;
		this.target = target;
		
	}
	public Class<?> getSource() {
		return source;
	}
	public Class<?> getTarget() {
		return target;
	}
	
}
