package test;

import java.io.File;
import java.io.IOException;
import java.io.Writer;
import java.nio.charset.StandardCharsets;

import org.nill.vorlagen.vorlagen.VorlageConsumer;

public class TestVorlage extends VorlageConsumer<TestModell> {

    public TestVorlage() {
		super(StandardCharsets.UTF_8,true);
	}

	@Override
	public void erzeugeAusgabe(Writer writer, TestModell modell) throws IOException {
		writer.write(modell.getDateiname());
	}

	@Override
	public File getAusgabe(TestModell modell, File ausgabeVerzeichnis) {
		return new File(ausgabeVerzeichnis,modell.getDateiname());
	}

}
