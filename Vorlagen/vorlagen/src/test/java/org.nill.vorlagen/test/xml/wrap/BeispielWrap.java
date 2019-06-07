package test.xml.wrap;

import org.nill.vorlagen.ST.xml.WrapElement;

public class BeispielWrap extends WrapElement {

    public BeispielWrap() {
        super();
    }

    public String getAName() {
        return "action" + getElem().getAttributeValue("name");
    }
 
    public String getGName() {
        return "global" + getElem().getAttributeValue("name");
    }
  
    public String getOName() {
        return "object" + getElem().getAttributeValue("name");
    }

    public String getCName() {
        return "create" + getElem().getAttributeValue("name");
    }
    
    public String getObjectClassName() {
        String className = getElem().getAttributeValue("class");
        return (className == null) ? "Object" : className;
    }

}
