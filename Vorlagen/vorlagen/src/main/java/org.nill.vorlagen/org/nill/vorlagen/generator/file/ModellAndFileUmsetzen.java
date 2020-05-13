package org.nill.vorlagen.generator.file;

import java.util.ArrayList;
import java.util.List;
import java.util.List;
import java.util.function.Function;

public class ModellAndFileUmsetzen<Q,Z>  implements Function<ModellAndFile<Q>, List<ModellAndFile<Z>>>{
	private Function<Q,List<Z>> modellTransform;
	
	public ModellAndFileUmsetzen(Function<Q, List<Z>> modellTransform) {
		super();
		this.modellTransform = modellTransform;
	}

	@Override
	public List<ModellAndFile<Z>> apply(ModellAndFile<Q> x) {
		List<ModellAndFile<Z>> liste = new ArrayList<>();
		for ( Z z : modellTransform.apply(x.modell)) {
			liste.add(new ModellAndFile<Z>(z,x.file));
		}
		return liste;
	}

}
