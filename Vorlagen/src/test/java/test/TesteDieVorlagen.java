package test;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

import org.junit.Assert;
import org.junit.Test;


public class TesteDieVorlagen {

    public TesteDieVorlagen() {
    }

    @Test
    public void test() {

        TestMaschine maschine = new TestMaschine("test", "modelle", "vorlagen",
                "target", new TestModellFabrik());
        new TestVorlagenDefinition("testVorlage", ".", ".",
                "java", new TestVorlagenFabrik(),
                new TestVorlagenModelFabrik(), maschine, StandardCharsets.UTF_8);
        try {
            File f = new File("target/java/package/test");
            if (f.exists()) {
                f.delete();
            }
            maschine.erzeugeAusgabe();
            if (!f.exists()) {
                Assert.fail("Datei " + f.getAbsolutePath() + " wurde nicht angelegt ");
            }
            
        } catch (Exception e) {
            e.printStackTrace();
            Assert.fail();
        }
    }

    @Test
    public void xmlTest() {
        
    }

}
