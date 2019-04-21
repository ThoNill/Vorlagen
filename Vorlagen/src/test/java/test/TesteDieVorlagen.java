package test;

import java.io.File;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

import org.junit.Assert;
import org.junit.Test;
import org.nill.generator.Generator;
import org.nill.lists.ListTransform;
import org.nill.vorlagen.VorlageGenerator;
import org.nill.vorlagen.VorlageConsumer;


public class TesteDieVorlagen {

    public TesteDieVorlagen() {
    }

    @Test
    public void test() {

    	try {
    	VorlageGenerator<TestModell,String,TestModell> ausgabe = new  VorlageGenerator("test", new TestModellFabrik(),
    			new TestVorlagenModellFabrik(),
    			new TestVorlage() , 
    			new File("./target/java/package"));

            File f = new File("./target/java/package/test");
            if (f.exists()) {
                f.delete();
            }
            ausgabe.erzeugeAusgabe();
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
