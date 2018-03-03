package janusAngularj2Frontend;

import java.util.ArrayList;
import java.util.List;

import nu.xom.Attribute;
import nu.xom.Document;
import nu.xom.Element;
import nu.xom.Node;

import org.nill.modelle.xml.XMLModelFabrik;
import org.nill.vorlagen.ModellFabrik;
import org.nill.vorlagen.VorlagenModellFabrik;

public abstract class ChangeXMLModel implements
VorlagenModellFabrik<Document, Document>,
ModellFabrik<Document, String>{

    String[] ignoriereElemente = { "STRINGSET", "MAPTABLESET", "SQLSET",
                "BEANSET", "ACTIONSET", "GLOBALSET", "BATCHSET", "NAMESPACE" };
 
    public ChangeXMLModel() {
        super();
    }

    @Override
    public Document erzeugeModell(String dateiName) {
        XMLModelFabrik xmlFabrik = new XMLModelFabrik();
        return changeDocument(xmlFabrik.erzeugeModell(dateiName));
    }

    @Override
    public List<Document> erzeugeVorlagenModelle(Document model) throws Exception {
        List<Document> list = new ArrayList<Document>();
        list.add(changeDocument(model));
        return list;
    }

    protected Document changeDocument(Document source) {
        return new Document(changeElement(source.getRootElement()));
    }
    
    abstract protected Element changeElement(Element source);

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

    abstract  protected Attribute changeAttribute(Element source, Attribute attr);
    
    protected boolean istDerNameImArrayEnthalten(String gesucht, String[] namen) {
        for (String name : namen) {
            if (name.equals(gesucht)) {
                return true;
            }
        }
        return false;
    }

    protected boolean istEinElementVomTyp(Element source, String name) {
        return name.equals(source.getLocalName().toUpperCase());
    }

}