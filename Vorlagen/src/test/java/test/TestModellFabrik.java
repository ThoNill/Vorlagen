package test;

import org.nill.vorlagen.ModellFabrik;

public class TestModellFabrik implements ModellFabrik<TestModell,String>{

    public TestModellFabrik() {
    }
    
    @Override
    public TestModell erzeugeModell(String beschreibung) {
        return new TestModell(beschreibung);
    };


}
