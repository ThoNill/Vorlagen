package org.nill.vorlagen.files;

import java.io.File;

public class ModellAndFile<M>{
	public M modell;
	public File file;

	public ModellAndFile(M modell, File file) {
		super();
		this.modell = modell;
		this.file = file;
	}
}
