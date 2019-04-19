package org.nill.reactive;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class ModellAnFileUmsetzen<Q,Z>  implements ListTransform<ModellAndFile<Q>, ModellAndFile<Z>>{
	private ListTransform<Q,Z> modellTransform;
	
	public ModellAnFileUmsetzen(ListTransform<Q, Z> modellTransform) {
		super();
		this.modellTransform = modellTransform;
	}

	@Override
	public List<ModellAndFile<Z>> transform(ModellAndFile<Q> x) {
		List<ModellAndFile<Z>> liste = new ArrayList<>();
		for ( Z z : modellTransform.transform(x.modell)) {
			liste.add(new ModellAndFile<Z>(z,x.file));
		}
		return liste;
	}

}
