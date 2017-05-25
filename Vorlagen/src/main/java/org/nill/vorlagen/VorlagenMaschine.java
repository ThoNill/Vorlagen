package org.nill.vorlagen;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class VorlagenMaschine<MODELL, MODELL_BESCHREIBUNG, VORLAGEN_MODELL, VORLAGEN_BESCHREIBUNG> {
    private String basisModellVerzeichnis;
    private String basisVorlagenVerzeichnis;
    private String basisZielVerzeichnis;
    private ModellFabrik<MODELL, MODELL_BESCHREIBUNG> modellFabrik;
    private List<VorlagenDefinition<MODELL, MODELL_BESCHREIBUNG, VORLAGEN_MODELL, VORLAGEN_BESCHREIBUNG>> definitionen;
    private MODELL_BESCHREIBUNG modellBeschreibung;

    public VorlagenMaschine(MODELL_BESCHREIBUNG modellBeschreibung,String basisModellVerzeichnis,
            String basisVorlagenVerzeichnis, String basisZielVerzeichnis,
            ModellFabrik<MODELL, MODELL_BESCHREIBUNG> modellFabrik) {
        super();
        definitionen = new ArrayList<VorlagenDefinition<MODELL, MODELL_BESCHREIBUNG, VORLAGEN_MODELL, VORLAGEN_BESCHREIBUNG>>();
        this.modellBeschreibung = modellBeschreibung;
        this.basisModellVerzeichnis = basisModellVerzeichnis;
        this.basisVorlagenVerzeichnis = basisVorlagenVerzeichnis;
        this.basisZielVerzeichnis = basisZielVerzeichnis;
        this.modellFabrik = modellFabrik;
    }


    public boolean add(
            VorlagenDefinition<MODELL, MODELL_BESCHREIBUNG, VORLAGEN_MODELL, VORLAGEN_BESCHREIBUNG> e) {
        return definitionen.add(e);
    }

    public void erzeugeAusgabe() throws IOException {
            MODELL modell = modellFabrik.erzeugeModell(modellBeschreibung);
            for (VorlagenDefinition<MODELL,MODELL_BESCHREIBUNG,  VORLAGEN_MODELL, VORLAGEN_BESCHREIBUNG> definition : definitionen) {
                definition.erzeugeAusgabe(modell);
            }
    }

    public String getBasisModellVerzeichnis() {
        return basisModellVerzeichnis;
    }

    public String getBasisVorlagenVerzeichnis() {
        return basisVorlagenVerzeichnis;
    }

    public String getBasisZielVerzeichnis() {
        return basisZielVerzeichnis;
    }

    public ModellFabrik<MODELL, MODELL_BESCHREIBUNG> getModellFabrik() {
        return modellFabrik;
    }
}
