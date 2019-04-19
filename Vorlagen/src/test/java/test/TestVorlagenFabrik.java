package test;

import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.nill.vorlagen.Vorlage;
import org.nill.vorlagen.VorlagenFabrik;

public class TestVorlagenFabrik implements VorlagenFabrik<TestModell,TestModell,String>{

    public TestVorlagenFabrik() {
    	super();
    }
    
	@Override
	public Vorlage<TestModell, TestModell, String> erzeugeVorlage(String beschreibung) {
		return new TestVorlage(this,StandardCharsets.UTF_8);
	}

	@Override
	public List<TestModell> erzeugeVorlagenModell(TestModell modell) throws Exception {
		List<TestModell> liste = new ArrayList<>();
		liste.add(modell);
		return liste;
	}
}
