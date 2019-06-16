package org.nill.vorlagen.generator.file;

import java.io.File;
import java.util.function.Function;

public class FileDazu<M> implements Function<M,ModellAndFile<M>>{
	private String file;
	
	public FileDazu(String file) {
		super();
		this.file = file;
	}

	@Override
	public ModellAndFile<M> apply(M modell) {
		return new ModellAndFile<>(modell, file);
	}

}
