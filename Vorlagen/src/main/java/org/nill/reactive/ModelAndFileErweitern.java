package org.nill.reactive;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class ModelAndFileErweitern<M>  implements ListTransform<ModellAndFile<M>, ModellAndFile<M>>{

	@Override
	public List<ModellAndFile<M>> transform(ModellAndFile<M> x) {
		DateienEinesVerzeichnisses d = new DateienEinesVerzeichnisses();
		List<ModellAndFile<M>> liste = new ArrayList<>();
		for ( File f : d.transform(x.file)) {
			liste.add(new ModellAndFile<M>(x.modell, f));
		}
		return liste;
	}

}