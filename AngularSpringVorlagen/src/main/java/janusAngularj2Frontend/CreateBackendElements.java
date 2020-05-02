package janusAngularj2Frontend;

import nu.xom.Attribute;
import nu.xom.Element;
import nu.xom.Nodes;

public class CreateBackendElements extends ChangeXMLModel {
    
    public CreateBackendElements() {
    }

    @Override
	protected Element changeElement(Element source) {

        Element newElement = new Element(source.getLocalName());

        if (istEinElementVomTyp(source, "SELECTION")) {
            String sqlName = source.getAttributeValue("source");
            Nodes sqls = source.getDocument().query("/descendant-or-self::SQL[@name='" + sqlName+"']");
            if(sqls.size()==1) {
                Element sqlElement = (Element)sqls.get(0);
                Element newSqlElement = changeElement(sqlElement);
                newElement.appendChild(newSqlElement);
            }
            dieAttributeHinzufügen(newElement, source);
        
        } else if (istEinElementVomTyp(source, "BATCH")) {
            String sqlName = source.getAttributeValue("foreach");
            Nodes sqls = source.getDocument().query("/descendant-or-self::SQL[@name='" + sqlName+"']");
            if(sqls.size()==1) {
                Element sqlElement = (Element)sqls.get(0);
                Element newSqlElement = changeElement(sqlElement);
                newElement.appendChild(newSqlElement);
            } else {
                Nodes  selections = source.getDocument().query("/descendant-or-self::SELECTION[@name='" + sqlName+"']");
                if(selections.size()==1) {
                    Element selectionElement = (Element)selections.get(0);
                    Element newSqlElement = changeElement(selectionElement);
                    newElement.appendChild(newSqlElement);
                }   
            }
            dieKinderHinzufügen(newElement, source);
            dieAttributeHinzufügen(newElement, source);
        
        } else if (istEinElementVomTyp(source, "CALLREF")) {
               String callName = source.getAttributeValue("ref");
            
            Nodes calls = source.getDocument().query("/descendant-or-self::CALL[@name='" + callName+ "']");
            if(calls.size()==1) {
                Element callElement = (Element)calls.get(0);
                Element newBeanElement = new Element("BEAN");
                dieAttributeHinzufügen(newBeanElement,(Element) callElement.getParent());

                Element newCallElement = changeElement(callElement);
                newBeanElement.appendChild(newCallElement);
                newElement.appendChild(newBeanElement);
            }
            dieAttributeHinzufügen(newElement, source);
        } else {
            dieKinderHinzufügen(newElement, source);
            dieAttributeHinzufügen(newElement, source);
        }
        return newElement;
    }

    @Override
	protected Attribute changeAttribute(Element source, Attribute attr) {
       return new Attribute(attr);
    }
}
