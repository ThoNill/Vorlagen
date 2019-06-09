package org.nill.vorlagen.st;

import java.io.File;
import java.nio.charset.Charset;
import java.util.function.Consumer;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.nill.vorlagen.generator.file.ModellAndFile;

public class STConsumer<M> extends STConsumerBasis<M> 
implements Consumer<ModellAndFile<ModellAndFile<M>>> {
	static Logger logger = Logger.getLogger(STConsumer.class.getSimpleName());

	
	public STConsumer(Charset charset) {
		super(charset);
	}

	@Override
	public void accept(ModellAndFile<ModellAndFile<M>> t) {
		ModellAndFile<M> mf = t.modell;
		File ausgabeVerzeichnis = t.file;
		File vorlageDatei = mf.file;
		M vorlageModell = mf.modell;

		try {
			erzeugeAusgabe(vorlageModell, vorlageDatei, ausgabeVerzeichnis);
		} catch (Exception e) {
			logger.log(Level.SEVERE, "Fehler in accept");
			
		}

	}
	

}
