package org.nill.vorlagen;

import java.util.List;

public interface VorlagenFabrik<MODELL, VORLAGEN_MODELL,VORLAGEN_BESCHREIBUNG> {
	Vorlage<MODELL, VORLAGEN_MODELL,VORLAGEN_BESCHREIBUNG> erzeugeVorlage(VORLAGEN_BESCHREIBUNG beschreibung);
	List<VORLAGEN_MODELL> erzeugeVorlagenModell(MODELL model) throws Exception;

}
