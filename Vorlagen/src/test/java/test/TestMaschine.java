package test;

import org.nill.vorlagen.ModellFabrik;
import org.nill.vorlagen.VorlagenMaschine;

public class TestMaschine extends VorlagenMaschine<TestModell, String,TestModell,String>{

    public TestMaschine(String modellBeschreibung,
            String basisModellVerzeichnis, String basisVorlagenVerzeichnis,
            String basisZielVerzeichnis,
            ModellFabrik<TestModell, String> modellFabrik) {
        super(modellBeschreibung, basisModellVerzeichnis, basisVorlagenVerzeichnis,
                basisZielVerzeichnis, modellFabrik);
    }

    

}
