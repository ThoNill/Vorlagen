package org.nill.vorlagen;

public interface VorlagenFabrik<VORLAGEN_MODELL,VORLAGEN_BESCHREIBUNG> {
    public Vorlage<VORLAGEN_MODELL>  erzeugeVorlage(VORLAGEN_BESCHREIBUNG beschreibung);

}
