package org.nill.vorlagen.st;

import java.nio.charset.Charset;
import java.util.function.Consumer;
import java.util.logging.Logger;

import org.nill.vorlagen.generator.file.ModellAndFile;
import org.nill.vorlagen.vorlagen.RuntimeVorlagenException;

public class STConsumer<M> extends STConsumerBasis<M> 
implements Consumer<ModellAndFile<ModellAndFile<M>>> {
	static Logger logger = Logger.getLogger(STConsumer.class.getSimpleName());

	
	public STConsumer(Charset charset) {
		super(charset);
	}

	@Override
	public void accept(ModellAndFile<ModellAndFile<M>> t) {
		ModellAndFile<M> mf = t.modell;
		String ausgabeVerzeichnis = t.file;
		String vorlageDatei = mf.file;
		M vorlageModell = mf.modell;

		try {
			erzeugeAusgabe(vorlageModell, vorlageDatei, ausgabeVerzeichnis);
		} catch (Exception e) {
			throw new RuntimeVorlagenException( "Fehler in accept",e);
		}

	}
	

}
