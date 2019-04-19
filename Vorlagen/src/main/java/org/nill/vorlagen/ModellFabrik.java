package org.nill.vorlagen;

public interface ModellFabrik<MODELL, MODELL_BESCHREIBUNG> {
	public MODELL erzeugeModell(MODELL_BESCHREIBUNG beschreibung);

}
