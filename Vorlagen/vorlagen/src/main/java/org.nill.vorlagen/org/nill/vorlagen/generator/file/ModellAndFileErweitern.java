package org.nill.vorlagen.generator.file;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.nill.vorlagen.files.DateienEinesVerzeichnisses;

public class ModellAndFileErweitern<M>  implements Function<ModellAndFile<M>,List<ModellAndFile<M>>>
{
	static Logger logger = Logger.getLogger(ModellAndFileErweitern.class.getSimpleName());

	public ModellAndFileErweitern() {
		super();
	}

	@Override
	public List<ModellAndFile<M>> apply(ModellAndFile<M> x) {
		logger.log(Level.INFO,"Durchsuche {0}",x.file);
		DateienEinesVerzeichnisses d = new DateienEinesVerzeichnisses();
		List<ModellAndFile<M>> liste = new ArrayList<>();
		for ( String f : d.apply(x.file)) {
			logger.log(Level.INFO,"Finde {0}",f);
			liste.add(new ModellAndFile<M>(x.modell, f));
		}
		return liste;
	}

}
