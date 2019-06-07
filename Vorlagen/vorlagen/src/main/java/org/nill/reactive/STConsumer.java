package org.nill.reactive;

import java.io.File;
import java.nio.charset.Charset;
import java.util.function.Consumer;

import org.nill.files.ModellAndFile;

public class STConsumer<M> extends STConsumerBasis<M> 
implements Consumer<ModellAndFile<ModellAndFile<M>>> {
	
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
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
	

}
