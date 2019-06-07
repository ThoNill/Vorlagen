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
import java.util.function.Consumer;

import org.nill.files.ModellAndFile;

public abstract class VorlageConsumer<VORLAGEN_MODELL> implements Consumer<ModellAndFile<VORLAGEN_MODELL>> {

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
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	private void erzeugeAusgabeAusVorlageModel(VORLAGEN_MODELL vm, File ausgabeVerzeichnis)
			throws IOException, FileNotFoundException {
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
