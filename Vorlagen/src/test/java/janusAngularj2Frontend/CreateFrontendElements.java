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

public class CreateFrontendElements implements VorlagenModellFabrik<Document, Document>, ModellFabrik<Document, String>{
    String[] datenQuellen = { "STRING", "MAPTABLE", "SQL", "MUTABLE", "BEAN","CALL", "SELECTION" };
    String[] sql‹bergehen = { "stmt", "contStmt", "prepare","countOn" };
    
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

    Document changeDocument(Document source) {
        return new Document(changeElement(source.getRootElement()));
    }
    
    Element changeElement(Element source) {
        Element newElement = new Element("app-"
                + source.getLocalName().toLowerCase());

        for (int i = 0; i < source.getChildCount(); i++) {
            Node node = source.getChild(i);
            if (node instanceof Element) {
                newElement.appendChild(changeElement((Element) node));
            } else {
                newElement.appendChild(node.copy());
            }
        }
        for (int i = 0; i < source.getAttributeCount(); i++) {
            Attribute attr = source.getAttribute(i);
            Attribute neuesAttribut = changeAttribute(source, attr);
            if(neuesAttribut != null) {
                newElement.addAttribute(neuesAttribut);
            }

        }
        return newElement;
    }

    private Attribute changeAttribute(Element source, Attribute attr) {
        if ("SQL".equals(source.getLocalName()) && istNameIstImArrayEnthalten(attr.getLocalName(),sql‹bergehen)) {
            return null;
        } 
        if ("name".equals(attr.getLocalName()) && !istNameIstImArrayEnthalten(source.getLocalName(),datenQuellen)) {
            return new Attribute("modell", attr.getValue());
        } else {
            return new Attribute(attr);
        }
    }

    protected boolean istNameIstImArrayEnthalten(String gesucht,String[] namen) {
        for (String name : namen) {
            if (name.equals(gesucht)) {
                return true;
            }
        }
        return false;
    }

}
