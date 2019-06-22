package org.nill.vorlagen.generator.file;

public class ModellAndFile<M>{
	public final M modell;
	public final String file;

	public ModellAndFile(M modell, String file) {
		super();
		this.modell = modell;
		this.file = file;
	}
}
