package test;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

import org.nill.reactive.ListTransform;

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
