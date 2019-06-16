package org.nill.vorlagen.generator.file;

import java.util.ArrayList;
import java.util.List;

import org.nill.vorlagen.files.DateienEinesVerzeichnisses;
import org.nill.vorlagen.lists.ListTransform;

public class ModellAndFileErweitern<M>  implements ListTransform<ModellAndFile<M>, ModellAndFile<M>>{
	
	public ModellAndFileErweitern() {
		super();
	}

	@Override
	public List<ModellAndFile<M>> transform(ModellAndFile<M> x) {
		System.out.println("Durchsuche " + x.file);
		DateienEinesVerzeichnisses d = new DateienEinesVerzeichnisses();
		List<ModellAndFile<M>> liste = new ArrayList<>();
		for ( String f : d.transform(x.file)) {
			System.out.println("Finde " + f);
			liste.add(new ModellAndFile<M>(x.modell, f));
		}
		return liste;
	}

}
