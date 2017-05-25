package org.nill.verzeichnisse;

public class VerzeichnisModell implements MitVerzeichnis{
    private String verzeichnis;

    public VerzeichnisModell(String verzeichnis) {
        super();
        this.verzeichnis = verzeichnis;
    }

    @Override
    public String getVerzeichnis() {
        return verzeichnis;
    }



}
