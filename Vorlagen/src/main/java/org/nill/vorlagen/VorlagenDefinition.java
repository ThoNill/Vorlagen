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

public class VorlagenDefinition<MODELL, MODELL_BESCHREIBUNG,VORLAGEN_MODELL,VORLAGEN_BESCHREIBUNG> {
    private Charset charset = StandardCharsets.UTF_8;
    private String modellVerzeichnis;
    private String vorlagenVerzeichnis;
    private String zielVerzeichnis;
    private VorlagenFabrik<VORLAGEN_MODELL,VORLAGEN_BESCHREIBUNG> vorlagenFabrik;
    private VorlagenModellFabrik<VORLAGEN_MODELL,MODELL> modellFabrik;
    private VorlagenMaschine<MODELL, MODELL_BESCHREIBUNG, VORLAGEN_MODELL, VORLAGEN_BESCHREIBUNG> maschine;
    private VORLAGEN_BESCHREIBUNG vorlagenBeschreibung;
    
    public VorlagenDefinition(VORLAGEN_BESCHREIBUNG vorlagenBeschreibung,String modellVerzeichnis,
            String vorlagenVerzeichnis, String zielVerzeichnis,
            VorlagenFabrik<VORLAGEN_MODELL,VORLAGEN_BESCHREIBUNG> vorlagenFabrik,
            VorlagenModellFabrik<VORLAGEN_MODELL, MODELL> modellFabrik,
            VorlagenMaschine<MODELL, MODELL_BESCHREIBUNG, VORLAGEN_MODELL, VORLAGEN_BESCHREIBUNG> maschine,Charset charSet) {
        super();
        this.vorlagenBeschreibung = vorlagenBeschreibung;
        this.modellVerzeichnis = modellVerzeichnis;
        this.vorlagenVerzeichnis = vorlagenVerzeichnis;
        this.zielVerzeichnis = zielVerzeichnis;
        this.vorlagenFabrik = vorlagenFabrik;
        this.modellFabrik = modellFabrik;
        this.maschine = maschine;
        this.charset = charSet;
        maschine.add(this);
    }
    

    public String getModellVerzeichnis() {
        return maschine.getBasisModellVerzeichnis() + File.separatorChar + modellVerzeichnis;
    }

    public String getVorlagenVerzeichnis() {
        return maschine.getBasisVorlagenVerzeichnis() + File.separatorChar  + vorlagenVerzeichnis;
    }

    public String getZielVerzeichnis() {
        return maschine.getBasisZielVerzeichnis()+ File.separatorChar  + zielVerzeichnis;
    }

    public void erzeugeAusgabe(MODELL modell) throws IOException {
        Vorlage<VORLAGEN_MODELL> vorlage = vorlagenFabrik.erzeugeVorlage(vorlagenBeschreibung);
        List<VORLAGEN_MODELL> vorlagenModelle = modellFabrik.erzeugeVorlagenModelle(modell);
        for(VORLAGEN_MODELL vorlagenModell : vorlagenModelle) {
            String dateiName = getZielVerzeichnis() + File.separatorChar + vorlage.getPfadMitDateiName(vorlagenModell);
            erzeugeEventuellFehlendeVerzeichnisse(dateiName);
            Writer writer = erzeugeWriter(dateiName);
            vorlage.erzeugeAusgabe(writer,vorlagenModell);
            writer.close();
        }
    }


    protected void erzeugeEventuellFehlendeVerzeichnisse(String dateiName)
            throws IOException {
        File f = new File(dateiName);
        Files.createDirectories(f.getParentFile().toPath());
    }

    protected BufferedWriter erzeugeWriter(String dateName)
            throws FileNotFoundException {
        return new BufferedWriter(new OutputStreamWriter(new FileOutputStream(dateName),charset));
    }


}
