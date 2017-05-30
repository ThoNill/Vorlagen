package janusAngularj2Frontend;

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

public class CreateFrontendElements implements
        VorlagenModellFabrik<Document, Document>,
        ModellFabrik<Document, String> {
    String[] datenQuellen = { "STRING", "MAPTABLE", "SQL", "MUTABLE", "BEAN",
            "CALL", "SELECTION", "COLUMN" };
    String[] ignoriereElemente = { "STRINGSET", "MAPTABLESET", "SQLSET",
            "BEANSET", "ACTIONSET", "GLOBALSET", "BATCHSET", "NAMESPACE" };
    String[] sqlÜbergehen = { "stmt", "contStmt", "prepare", "countOn" };

    public CreateFrontendElements() {
    }

    @Override
    public Document erzeugeModell(String dateiName) {
        XMLModelFabrik xmlFabrik = new XMLModelFabrik();
        return changeDocument(xmlFabrik.erzeugeModell(dateiName));
    }

    @Override
    public List<Document> erzeugeVorlagenModelle(Document model)
            throws Exception {
        List<Document> list = new ArrayList<Document>();
        list.add(changeDocument(model));
        return list;
    }

    protected Document changeDocument(Document source) {
        return new Document(changeElement(source.getRootElement()));
    }

    protected Element changeElement(Element source) {

        String elementName = "app-" + source.getLocalName().toLowerCase();
        if (istEinElementVomTyp(source, "COLUMN") && !istEinElementVomTyp((Element)source.getParent(),"SHOWTABLE")) {
            elementName = "app-mu" + source.getLocalName().toLowerCase();
        }
        Element newElement = new Element(elementName);

        if (istEinElementVomTyp(source, "LABEL")) {
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

    protected void dieKinderHinzufügen(Element newElement, Element source) {
        for (int i = 0; i < source.getChildCount(); i++) {
            Node node = source.getChild(i);
            if (node instanceof Element) {
                Element childElement = (Element) node;
                if (istDerNameImArrayEnthalten(childElement.getLocalName(),
                        ignoriereElemente)) {
                    dieKinderHinzufügen(newElement, childElement);
                } else {
                    newElement.appendChild(changeElement(childElement));
                }
            } else {
                newElement.appendChild(node.copy());
            }
        }
    }

    protected void dieAttributeHinzufügen(Element newElement, Element source) {
        for (int i = 0; i < source.getAttributeCount(); i++) {
            Attribute attr = source.getAttribute(i);
            Attribute neuesAttribut = changeAttribute(source, attr);
            if (neuesAttribut != null) {
                newElement.addAttribute(neuesAttribut);
            }

        }
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

    protected boolean istDerNameImArrayEnthalten(String gesucht, String[] namen) {
        for (String name : namen) {
            if (name.equals(gesucht)) {
                return true;
            }
        }
        return false;
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

    protected boolean istEinElementVomTyp(Element source, String name) {
        return name.equals(source.getLocalName().toUpperCase());
    }
}
