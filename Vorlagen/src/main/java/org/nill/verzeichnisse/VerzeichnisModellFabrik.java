package org.nill.verzeichnisse;

import org.nill.vorlagen.ModellFabrik;

public class VerzeichnisModellFabrik implements ModellFabrik<VerzeichnisModell, String>{
   

    public VerzeichnisModellFabrik() {
        super();
    }

    @Override
    public VerzeichnisModell erzeugeModell(String verzeichnis) {
       return new VerzeichnisModell(verzeichnis);
    }



}
