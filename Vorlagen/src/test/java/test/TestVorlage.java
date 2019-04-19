package test;

import java.io.File;
import java.io.IOException;
import java.io.Writer;
import java.nio.charset.Charset;

import org.nill.vorlagen.Vorlage;
import org.nill.vorlagen.VorlagenFabrik;

public class TestVorlage extends Vorlage<TestModell,TestModell,String>{

    public TestVorlage(VorlagenFabrik<TestModell, TestModell, String> vorlagenFabrik, Charset charSet) {
		super(vorlagenFabrik, charSet,true);
	}

	@Override
	public File getAusgabe(TestModell modell) {
		return new File("./target/java/package",modell.getDateiname());
	}

	@Override
	public void erzeugeAusgabe(Writer writer, TestModell modell) throws IOException {
		writer.write(modell.getDateiname());
	}

}
