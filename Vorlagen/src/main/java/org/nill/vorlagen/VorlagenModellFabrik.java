package org.nill.vorlagen;

import java.util.List;

public interface VorlagenModellFabrik<VORLAGEN_MODELL,MODELL> {
    public List<VORLAGEN_MODELL> erzeugeVorlagenModelle(MODELL model);

}
