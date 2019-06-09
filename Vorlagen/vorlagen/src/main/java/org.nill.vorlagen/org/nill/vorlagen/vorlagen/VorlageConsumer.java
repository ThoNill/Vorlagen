package org.nill.vorlagen.vorlagen;

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
import java.util.function.Consumer;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.nill.vorlagen.generator.file.ModellAndFile;

public abstract class VorlageConsumer<VORLAGEN_MODELL> implements Consumer<ModellAndFile<VORLAGEN_MODELL>> {
	private static final String FEHLER_IN_ERZEUGE_AUSGABE_AUS_VORLAGE_MODEL = "Fehler in erzeugeAusgabeAusVorlageModel";

	static Logger logger = Logger.getLogger(VorlageConsumer.class.getSimpleName());

	private Charset charset = StandardCharsets.UTF_8;
	private boolean überschreiben;

	public VorlageConsumer(Charset charSet, boolean überschreiben) {
		super();
		this.charset = charSet;
		this.überschreiben = überschreiben;

	}

	public abstract void erzeugeAusgabe(Writer writer, VORLAGEN_MODELL modell) throws IOException;

	public abstract File getAusgabe(VORLAGEN_MODELL modell, File ausgabeVerzeichnis);

	@Override
	public void accept(ModellAndFile<VORLAGEN_MODELL> vm) {
		try {
			erzeugeAusgabeAusVorlageModel(vm.modell, vm.file);
		} catch (IOException e) {
			logger.log(Level.SEVERE, FEHLER_IN_ERZEUGE_AUSGABE_AUS_VORLAGE_MODEL);
		}

	}

	private void erzeugeAusgabeAusVorlageModel(VORLAGEN_MODELL vm, File ausgabeVerzeichnis)
			throws IOException {
		String dateiName = getAusgabe(vm, ausgabeVerzeichnis).toString();
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
