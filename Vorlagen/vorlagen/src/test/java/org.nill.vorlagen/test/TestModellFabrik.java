package test;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

public class TestModellFabrik implements Function<String,List<TestModell>>{

    public TestModellFabrik() {
    }
    
    @Override
    public List<TestModell> apply(String beschreibung) {
        TestModell m = new TestModell(beschreibung);
        List<TestModell> liste = new ArrayList<>();
        liste.add(m);
        return liste;
    };


}
