package janusAngularj2Frontend;


import java.util.function.Function;

import nu.xom.Attribute;
import nu.xom.Document;
import nu.xom.Element;
import nu.xom.Node;

public abstract class ChangeXMLModel implements
Function<Document, Document>{

    String[] ignoriereElemente = { "STRINGSET", "MAPTABLESET", "SQLSET",
                "BEANSET", "ACTIONSET", "GLOBALSET", "BATCHSET", "NAMESPACE" };
 
    public ChangeXMLModel() {
        super();
    }


    @Override
    public Document apply(Document model)  {
       return changeDocument(model);
    }

    protected Document changeDocument(Document source) {
        return new Document(changeElement(source.getRootElement()));
    }
    
    abstract protected Element changeElement(Element source);

    protected void dieKinderHinzuf�gen(Element newElement, Element source) {
        for (int i = 0; i < source.getChildCount(); i++) {
            Node node = source.getChild(i);
            if (node instanceof Element) {
                Element childElement = (Element) node;
                if (istDerNameImArrayEnthalten(childElement.getLocalName(),
                        ignoriereElemente)) {
                    dieKinderHinzuf�gen(newElement, childElement);
                } else {
                    newElement.appendChild(changeElement(childElement));
                }
            } else {
                newElement.appendChild(node.copy());
            }
        }
    }

    protected void dieAttributeHinzuf�gen(Element newElement, Element source) {
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