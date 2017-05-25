package test;

import java.io.File;
import java.io.IOException;
import java.io.Writer;

import org.nill.vorlagen.Vorlage;

public class TestVorlage implements Vorlage<TestModell>{

    public TestVorlage() {
        // TODO Auto-generated constructor stub
    }

    public void erzeugeAusgabe(Writer writer, TestModell modell) throws IOException {
        writer.write(modell.getDateiname());
        
    }

    public String getPfadMitDateiName(TestModell modell) {
       return "package" + File.separatorChar + modell.getDateiname();
    }

}
