package org.nill.files;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.nill.lists.ListTransform;

public class ModellAndFileErweitern<M>  implements ListTransform<ModellAndFile<M>, ModellAndFile<M>>{

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
