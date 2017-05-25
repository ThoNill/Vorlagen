package org.nill.vorlagen;

import java.io.IOException;
import java.io.Writer;

public interface Vorlage<VORLAGEN_MODELL> {
    public void erzeugeAusgabe(Writer writer,VORLAGEN_MODELL modell) throws IOException;
    public String getPfadMitDateiName(VORLAGEN_MODELL modell);

}
