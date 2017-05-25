package test;

import java.util.ArrayList;
import java.util.List;

import org.nill.vorlagen.VorlagenModellFabrik;

public class TestVorlagenModelFabrik implements VorlagenModellFabrik<TestModell,TestModell>{

    public TestVorlagenModelFabrik() {
    }

    public List<TestModell> erzeugeVorlagenModelle(TestModell modell) {
        ArrayList<TestModell> liste = new ArrayList();
        liste.add(modell);
        return liste;
    }

}
