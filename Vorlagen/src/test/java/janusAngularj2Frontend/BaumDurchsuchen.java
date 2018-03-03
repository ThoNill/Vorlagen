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

public class BaumDurchsuchen  {
    
    public BaumDurchsuchen() {
    }
    
    public void dasDocumentDurchsuchen(Document source) {
        einElementDurchsuchen(source.getRootElement());
    }

    private void einElementDurchsuchen(Element source) {
        bearbeiteElement(source);
        dieAttributeDurchsuchen(source);
        dieKinderDurchsuchen(source);
    }


    private void dieKinderDurchsuchen(Element source) {
        for (int i = 0; i < source.getChildCount(); i++) {
            Node node = source.getChild(i);
            if (node instanceof Element) {
                einElementDurchsuchen((Element) node);
            } else {
                bearbeiteKnoten(node);
            }
        }
    }

    private void dieAttributeDurchsuchen( Element source) {
        for (int i = 0; i < source.getAttributeCount(); i++) {
            Attribute attr = source.getAttribute(i);
            bearbeiteAttribut(attr);
    
        }
    }

    protected void bearbeiteAttribut(Attribute attr) {
    }
    
    protected void bearbeiteKnoten(Node knoten) {
    }
    
    protected void bearbeiteElement(Element element) {
       
    }
    
    protected boolean hatEinenNamenAus(Attribute attribut, String[] attributNamen) {
        for(String name : attributNamen) {
            if (name.equals(attribut.getLocalName())) {
                return true;
            }
        }
        return false;
    }
    
    protected boolean hatEinenNamenAus(Element element, String[] attributNamen) {
        for(String name : attributNamen) {
            if (name.equals(element.getLocalName())) {
                return true;
            }
        }
        return false;
    }
}

