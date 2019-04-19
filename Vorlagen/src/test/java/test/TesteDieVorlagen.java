package test;

import java.io.File;

import org.junit.Assert;
import org.junit.Test;


public class TesteDieVorlagen {

    public TesteDieVorlagen() {
    }

    @Test
    public void test() {

        TestMaschine maschine = new TestMaschine("test",  new TestModellFabrik());
        maschine.add(new TestVorlagenFabrik(),"vorlagenBeschreibung");
        try {
            File f = new File("./target/java/package/test");
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
