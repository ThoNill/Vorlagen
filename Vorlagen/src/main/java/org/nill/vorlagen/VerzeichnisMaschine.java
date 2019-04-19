package org.nill.vorlagen;

import java.io.File;
import java.util.List;
import org.nill.verzeichnisse.DateienEinesVerzeichnisses;

public abstract class VerzeichnisMaschine<MODELL, VORLAGEN_MODELL> extends GeneratorList {
	private List<File> modellBeschreibungen;
	private List<File> vorlageBeschreibungen;
	private File vorlageVerzeichnis;
	private File modellVerzeichnis;
	private ModellFabrik<MODELL, String> modelFabrik;
	VorlagenFabrik<MODELL, VORLAGEN_MODELL, File> vorlagenFabrik;

	public VerzeichnisMaschine(File modellVerzeichnis, File vorlageVerzeichnis,
			ModellFabrik<MODELL, String> modelFabrik, 
			VorlagenFabrik<MODELL, VORLAGEN_MODELL, File> vorlagenFabrik)
			throws Exception {
		super();
		this.vorlageVerzeichnis = vorlageVerzeichnis;
		this.modellVerzeichnis = modellVerzeichnis;
		this.modelFabrik = modelFabrik;
		this.vorlagenFabrik = vorlagenFabrik;
		modellBeschreibungen = (List<File>) new DateienEinesVerzeichnisses(modellVerzeichnis).erzeugeFileListe();
		vorlageBeschreibungen = (List<File>) new DateienEinesVerzeichnisses(vorlageVerzeichnis).erzeugeFileListe();
		createMaschinen();
	}

	public File getVorlageVerzeichnis() {
		return vorlageVerzeichnis;
	}

	public File getModellVerzeichnis() {
		return modellVerzeichnis;
	}

	public void createMaschinen() {
		for (File modellBeschreibung : modellBeschreibungen) {

			VorlagenMaschine<MODELL, String, VORLAGEN_MODELL, File> maschine = new VorlagenMaschine<>(
					modellBeschreibung.toString(), modelFabrik);

			for (File vorlageBeschreibung : vorlageBeschreibungen) {
				maschine.add(vorlagenFabrik, vorlageBeschreibung);
			}
			add(maschine);
		}
	}

}
