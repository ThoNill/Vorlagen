package org.nill.ST;

import java.io.File;

import org.nill.vorlagen.Vorlage;
import org.nill.vorlagen.VorlagenFabrik;

public abstract class STVorlagenFabrik<VORLAGEN_MODELL> implements VorlagenFabrik<VORLAGEN_MODELL,String> {
    String verzeichnis;

    public STVorlagenFabrik(String verzeichnis) {
        super();
        this.verzeichnis = verzeichnis;
    }

    @Override
    public Vorlage<VORLAGEN_MODELL> erzeugeVorlage(String beschreibung) {
        return erzeugeVorlageAusDatei(verzeichnis + File.separator + beschreibung);
    };

    public abstract Vorlage<VORLAGEN_MODELL> erzeugeVorlageAusDatei(String dateiName);


    
}
