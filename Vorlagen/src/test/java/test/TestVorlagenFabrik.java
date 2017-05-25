package test;

import org.nill.vorlagen.Vorlage;
import org.nill.vorlagen.VorlagenFabrik;

public class TestVorlagenFabrik implements VorlagenFabrik<TestModell,String>{

    public TestVorlagenFabrik() {
    }

    public Vorlage<TestModell> erzeugeVorlage(String beschreibung) {
       return new TestVorlage();
    }

}
