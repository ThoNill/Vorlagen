package org.nill.vorlagen.files;

import java.io.File;
import java.util.function.Function;

public class FileDazu<M> implements Function<M,ModellAndFile<M>>{
	private File file;
	
	public FileDazu(File file) {
		super();
		this.file = file;
	}

	@Override
	public ModellAndFile<M> apply(M modell) {
		return new ModellAndFile<>(modell, file);
	}

}
