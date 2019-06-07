package janusAngularj2Frontend;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

import nu.xom.Document;
import nu.xom.Element;

public class BeanVariablenSammeln extends VariablenNutzungSammeln{
    HashMap<String,ElementHelper> beans = new HashMap<>();
    
    
     public BeanVariablenSammeln() {
       
    }
    
    @Override
    protected void bearbeiteElement(Element element) {
         super.bearbeiteElement(element);
         if ("@BEAN".equals(element.getLocalName())) {
             beans.put(element.getLocalName(),new ElementHelper(element));
         }
                 
    }
    
    @Override
	public void dasDocumentDurchsuchen(Document source) {
        super.dasDocumentDurchsuchen(source);
        for(String name : getVariablenNamen()) {
            if (name.contains(".")) {
                String potentiellerBeanName = name.substring(0, name.indexOf('.'));
                if (beans.containsKey(potentiellerBeanName)) {
                    beans.get(potentiellerBeanName).add(name);
                }
            }
        }
        
    }
    
    public List<String> getFeldNamen(String name) {
         List<String> feldNamen = Collections.emptyList();
        if (beans.containsKey(name)) {
            return beans.get(name).felder;
        }
        return feldNamen;
    }
    

    
}

class ElementHelper {
    Element element;
    List<String> felder;
    
    public ElementHelper(Element elem) {
        this.element = element;
        felder = new ArrayList<>();
    }
    
    public void add(String text) {
        String name = text.substring(text.indexOf('.')+1);
        if (!felder.contains(name)) {
            felder.add(name);
        }
    }
}
