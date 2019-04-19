package test;

import org.nill.vorlagen.ModellFabrik;
import org.nill.vorlagen.VorlagenMaschine;

public class TestMaschine extends VorlagenMaschine<TestModell, String,TestModell,String>{

	
    public TestMaschine(String modellBeschreibung,
            ModellFabrik<TestModell, String> modellFabrik) {
        super(modellBeschreibung, modellFabrik);
    }

    

}
