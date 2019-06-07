package org.nill.vorlagen.files;

import java.util.ArrayList;
import java.util.List;

import org.nill.vorlagen.lists.ListTransform;

public class ModellAndFileUmsetzen<Q,Z>  implements ListTransform<ModellAndFile<Q>, ModellAndFile<Z>>{
	private ListTransform<Q,Z> modellTransform;
	
	public ModellAndFileUmsetzen(ListTransform<Q, Z> modellTransform) {
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
