package test.xml;

import java.nio.charset.Charset;

import nu.xom.Document;

import org.nill.modelle.xml.XMLVerzeichnisVorlagenModellFabrik;
import org.nill.verzeichnisse.VerzeichnisModell;
import org.nill.vorlagen.VorlagenDefinition;
import org.nill.vorlagen.VorlagenFabrik;
import org.nill.vorlagen.VorlagenMaschine;
import org.nill.vorlagen.VorlagenModellFabrik;

public class XMLVorlagenDefinition extends
        VorlagenDefinition<VerzeichnisModell, String, Document, String> {

    public XMLVorlagenDefinition(
            String vorlagenBeschreibung,
            String modellVerzeichnis,
            String vorlagenVerzeichnis,
            String zielVerzeichnis,
            XMLSTVorlagenFabrik vorlagenFabrik,
            XMLVerzeichnisVorlagenModellFabrik modellFabrik,
            XMLMaschine maschine,
            Charset charSet) {
        super(vorlagenBeschreibung, modellVerzeichnis, vorlagenVerzeichnis,
                zielVerzeichnis, vorlagenFabrik, modellFabrik, maschine, charSet);
    }

}
