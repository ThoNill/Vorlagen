package org.nill.ST.vorlagen;

import java.io.File;
import java.nio.charset.StandardCharsets;
import java.util.function.Function;

import org.nill.vorlagen.FunctionFabrik;
import org.nill.vorlagen.Vorlage;

public abstract class STVorlagenFabrik<MODELL,VORLAGEN_MODELL> extends FunctionFabrik<MODELL,VORLAGEN_MODELL,File> {
	private File ausgabeVerzeichnis;
	protected boolean �berschreiben = true;
	
	public STVorlagenFabrik(Function<MODELL, VORLAGEN_MODELL> transformation,File ausgabeVerzeichnis) {
        this(transformation,ausgabeVerzeichnis,true);
    }


	public STVorlagenFabrik(Function<MODELL, VORLAGEN_MODELL> transformation,File ausgabeVerzeichnis,boolean �berschreiben) {
        super(transformation);
        this.ausgabeVerzeichnis = ausgabeVerzeichnis;
     }

	public boolean is�berschreiben() {
		return �berschreiben;
	}

	public File getAusgabeVerzeichnis() {
		return ausgabeVerzeichnis;
	}
   
    @Override
	public Vorlage<MODELL,VORLAGEN_MODELL,File> erzeugeVorlage(File beschreibung) {
    	return new STVorlage<MODELL, VORLAGEN_MODELL>(this,StandardCharsets.UTF_8, beschreibung.toString(), ausgabeVerzeichnis,�berschreiben);
    }

    
}
