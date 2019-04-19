package org.nill.vorlagen;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.List;
import java.util.Optional;

public abstract class Vorlage<MODELL, VORLAGEN_MODELL, VORLAGEN_BESCHREIBUNG> {
	private Charset charset = StandardCharsets.UTF_8;
	private VorlagenFabrik<MODELL, VORLAGEN_MODELL, VORLAGEN_BESCHREIBUNG> vorlagenFabrik;
	private boolean überschreiben;

	public Vorlage(VorlagenFabrik<MODELL, VORLAGEN_MODELL, VORLAGEN_BESCHREIBUNG> vorlagenFabrik, Charset charSet,
			boolean überschreiben) {
		super();
		this.vorlagenFabrik = vorlagenFabrik;
		this.charset = charSet;
		this.überschreiben = überschreiben;

	}

	public abstract void erzeugeAusgabe(Writer writer, VORLAGEN_MODELL modell) throws IOException;

	public abstract File getAusgabe(VORLAGEN_MODELL modell);

	public void erzeugeAusgabe(MODELL modell) throws Exception {
		List<VORLAGEN_MODELL> vorlagenModelle = vorlagenFabrik.erzeugeVorlagenModell(modell);
		for (VORLAGEN_MODELL vm : vorlagenModelle) {
			erzeugeAusgabeAusVorlageModel(vm);
		}
	}

	private void erzeugeAusgabeAusVorlageModel(VORLAGEN_MODELL vm) throws IOException, FileNotFoundException {
		String dateiName = getAusgabe(vm).toString();
		if (überschreiben || !(new File(dateiName)).exists()) {
			erzeugeEventuellFehlendeVerzeichnisse(dateiName);
			Writer writer = erzeugeWriter(dateiName);
			erzeugeAusgabe(writer, vm);
			writer.close();
		}
	}

	private void erzeugeEventuellFehlendeVerzeichnisse(String dateiName) throws IOException {
		File f = new File(dateiName);
		Files.createDirectories(f.getParentFile().toPath());
	}

	protected Writer erzeugeWriter(String dateName) throws FileNotFoundException {
		return new BufferedWriter(new OutputStreamWriter(new FileOutputStream(dateName), charset));
	}

}
