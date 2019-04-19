package org.nill.vorlagen;

import java.util.ArrayList;
import java.util.List;

public class VorlagenMaschine<MODELL, MODELL_BESCHREIBUNG, VORLAGEN_MODELL,VORLAGEN_BESCHREIBUNG>  implements Generator{
	private ModellFabrik<MODELL, MODELL_BESCHREIBUNG> modellFabrik;
	private List<Vorlage<MODELL, VORLAGEN_MODELL,VORLAGEN_BESCHREIBUNG>> definitionen;
	private MODELL_BESCHREIBUNG modellBeschreibung;

	public VorlagenMaschine(MODELL_BESCHREIBUNG modellBeschreibung,
			ModellFabrik<MODELL, MODELL_BESCHREIBUNG> modellFabrik) {
		super();
		definitionen = new ArrayList<Vorlage<MODELL, VORLAGEN_MODELL,VORLAGEN_BESCHREIBUNG>>();
		this.modellBeschreibung = modellBeschreibung;
		this.modellFabrik = modellFabrik;
	}

	public boolean add(VorlagenFabrik<MODELL, VORLAGEN_MODELL,VORLAGEN_BESCHREIBUNG> vorlagenFabrik,VORLAGEN_BESCHREIBUNG vorlagenBeschreibung) {

		Vorlage<MODELL, VORLAGEN_MODELL,VORLAGEN_BESCHREIBUNG> vorlage = vorlagenFabrik.erzeugeVorlage(vorlagenBeschreibung);

		return definitionen.add(vorlage);
	}

	@Override
	public void erzeugeAusgabe() throws Exception {
		MODELL modell = modellFabrik.erzeugeModell(modellBeschreibung);
		for (Vorlage<MODELL, VORLAGEN_MODELL,VORLAGEN_BESCHREIBUNG> definition : definitionen) {
			definition.erzeugeAusgabe(modell);
		}
	}

	public ModellFabrik<MODELL, MODELL_BESCHREIBUNG> getModellFabrik() {
		return modellFabrik;
	}

}
