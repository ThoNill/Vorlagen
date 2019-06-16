package org.nill.vorlagen.generator.file;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.nill.vorlagen.files.DateienEinesVerzeichnisses;
import org.nill.vorlagen.lists.ListTransform;

public class ModellAndFileErweitern<M>  implements ListTransform<ModellAndFile<M>, ModellAndFile<M>>{
	static Logger logger = Logger.getLogger(ModellAndFileErweitern.class.getSimpleName());

	public ModellAndFileErweitern() {
		super();
	}

	@Override
	public List<ModellAndFile<M>> transform(ModellAndFile<M> x) {
		logger.log(Level.INFO,"Durchsuche " + x.file);
		DateienEinesVerzeichnisses d = new DateienEinesVerzeichnisses();
		List<ModellAndFile<M>> liste = new ArrayList<>();
		for ( String f : d.transform(x.file)) {
			logger.log(Level.INFO,"Finde " + f);
			liste.add(new ModellAndFile<M>(x.modell, f));
		}
		return liste;
	}

}
