package janusAngularj2Frontend;

import janusAngular2Backend.wraps.SQL;

import java.util.ArrayList;
import java.util.List;

import org.nill.modelle.xml.XMLModelFabrik;
import org.nill.vorlagen.ModellFabrik;
import org.nill.vorlagen.VorlagenModellFabrik;

import nu.xom.Attribute;
import nu.xom.Document;
import nu.xom.Element;
import nu.xom.Node;
import nu.xom.Text;

public class CreateFrontendElements extends ChangeXMLModel {
    String[] datenQuellen = { "STRING", "MAPTABLE", "SQL", "MUTABLE", "BEAN",
            "CALL", "SELECTION", "COLUMN" };
    String[] sqlÜbergehen = { "stmt", "contStmt", "prepare", "countOn" };

    public CreateFrontendElements() {
    }

    protected Element changeElement(Element source) {

        String elementName = "app-" + source.getLocalName().toLowerCase();
        if (istEinElementVomTyp(source, "COLUMN") && !istEinElementVomTyp((Element)source.getParent(),"SHOWTABLE")) {
            elementName = "app-mu" + source.getLocalName().toLowerCase();
        } else  if (istEinElementVomTyp(source, "SQL")) {
            elementName = "app-mutable";
        }

        Element newElement = new Element(elementName);
        if (istEinElementVomTyp(source, "SQL")) {
            newElement.addAttribute(new Attribute("name",
                    source.getAttributeValue("name")));
            newElement.addAttribute(new Attribute("abfrageText",
                    "api/mfisher/" + source.getAttributeValue("name")));
            
            
            SQL wrap = new SQL();
            wrap.setElem(source);
            for(String parameter : wrap.getAbfrageParameter()) {
                Element setElement = new Element("app-set");
                setElement.addAttribute(new Attribute(parameter,
                        parameter));
                newElement.appendChild(setElement);
            }
        } else if (istEinElementVomTyp(source, "LABEL")) {
            Element templateElement = new Element("template");
            String aText = source.getAttributeValue("text");
            String aName = source.getAttributeValue("name");
            if (aName != null) {
                aText = ((aText == null) ? "" : aText + " ") + "?" + aName
                        + "?";
            }
            if (aText != null) {
                templateElement.appendChild(new Text(
                        ändereVariablen(ändereVariablen(aText))));
                templateElement.addAttribute(new Attribute("let-dialog",
                        "dialog"));
                templateElement.addAttribute(new Attribute("emptyAttribute",
                        "#text"));
            }
            newElement.appendChild(templateElement);
        } else {
            dieKinderHinzufügen(newElement, source);
            dieAttributeHinzufügen(newElement, source);
        }
        return newElement;
    }



    protected Attribute changeAttribute(Element source, Attribute attr) {
        if (istEinElementVomTyp(source, "SQL")
                && istDerNameImArrayEnthalten(attr.getLocalName(), sqlÜbergehen)) {
            return null;
        }
        if ("name".equals(attr.getLocalName())
                && !istDerNameImArrayEnthalten(source.getLocalName(),
                        datenQuellen)) {
            return new Attribute("modell", attr.getValue());
        } else {
            return new Attribute(attr);
        }
    }


    protected String ändereVariablen(String text) {
        String texte[] = text.split("\\?");
        boolean istVariablenName = false;
        StringBuilder builder = new StringBuilder();
        for (String abschnitt : texte) {
            if (istVariablenName) {
                builder.append("{{dialog.");
                builder.append(abschnitt);
                builder.append("|async}}");
            } else {
                builder.append(abschnitt);
            }
            istVariablenName = !istVariablenName;
        }
        return builder.toString();
    }

}

