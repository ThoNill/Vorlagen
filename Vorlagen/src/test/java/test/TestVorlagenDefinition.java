package test;

import java.nio.charset.Charset;

import org.nill.vorlagen.VorlagenDefinition;
import org.nill.vorlagen.VorlagenFabrik;
import org.nill.vorlagen.VorlagenMaschine;
import org.nill.vorlagen.VorlagenModellFabrik;

public class TestVorlagenDefinition extends VorlagenDefinition<TestModell, String, TestModell, String> {

    public TestVorlagenDefinition(String vorlagenBeschreibung,
            String modellVerzeichnis, String vorlagenVerzeichnis,
            String zielVerzeichnis,
            VorlagenFabrik<TestModell, String> vorlagenFabrik,
            VorlagenModellFabrik<TestModell, TestModell> modellFabrik,
            VorlagenMaschine<TestModell, String, TestModell, String> maschine,
            Charset charSet) {
        super(vorlagenBeschreibung, modellVerzeichnis, vorlagenVerzeichnis,
                zielVerzeichnis, vorlagenFabrik, modellFabrik, maschine, charSet);
    }

  

}
