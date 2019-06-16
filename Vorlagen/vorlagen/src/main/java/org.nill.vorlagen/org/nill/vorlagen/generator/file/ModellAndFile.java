package org.nill.vorlagen.generator.file;

import java.io.File;

public class ModellAndFile<M>{
	public M modell;
	public String file;

	public ModellAndFile(M modell, String file) {
		super();
		this.modell = modell;
		this.file = file;
	}
}
