package test;

import java.util.ArrayList;
import java.util.List;

import org.nill.lists.ListTransform;

public class TestModellFabrik implements ListTransform<String,TestModell>{

    public TestModellFabrik() {
    }
    
    @Override
    public List<TestModell> transform(String beschreibung) {
        TestModell m = new TestModell(beschreibung);
        List<TestModell> liste = new ArrayList<>();
        liste.add(m);
        return liste;
    };


}
