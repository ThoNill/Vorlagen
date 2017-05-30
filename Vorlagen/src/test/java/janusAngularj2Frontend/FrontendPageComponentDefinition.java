package janusAngularj2Frontend;

import java.io.File;
import java.nio.charset.Charset;

import nu.xom.Document;

import org.nill.ST.xml.XMLMaschine;
import org.nill.ST.xml.XMLVorlagenFabrik;
import org.nill.modelle.xml.XMLModelFabrik;
import org.nill.modelle.xml.XMLVerzeichnisVorlagenModellFabrik;
import org.nill.verzeichnisse.VerzeichnisModell;
import org.nill.vorlagen.VorlagenDefinition;

public class FrontendPageComponentDefinition extends
        VorlagenDefinition<VerzeichnisModell, String,Document, String> {

    public FrontendPageComponentDefinition(
            String vorlagenBeschreibung,
            String modellVerzeichnis,
            String vorlagenVerzeichnis,
            String zielVerzeichnis,
            String packageName,
            String defaultClass,
            XMLMaschine maschine,
            Charset charSet) {
        super(vorlagenBeschreibung, modellVerzeichnis, vorlagenVerzeichnis,
                zielVerzeichnis, 
                new XMLVorlagenFabrik(maschine.getBasisVorlagenVerzeichnis() + File.separatorChar  + vorlagenVerzeichnis,packageName,defaultClass),
                new  XMLVerzeichnisVorlagenModellFabrik(new XMLModelFabrik()),
                maschine, charSet);
    }

}
