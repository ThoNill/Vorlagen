package test;

import java.io.File;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

import org.junit.Assert;
import org.junit.Test;
import org.nill.reactive.Generator;
import org.nill.reactive.ListTransform;
import org.nill.reactive.VorlageAusgabe;
import org.nill.reactive.VorlageConsumer;


public class TesteDieVorlagen {

    public TesteDieVorlagen() {
    }

    @Test
    public void test() {

    	try {
    	VorlageAusgabe<TestModell,String,TestModell> ausgabe = new  VorlageAusgabe("test", new TestModellFabrik(),
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
