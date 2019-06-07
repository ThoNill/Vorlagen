package janusAngularj2Frontend;

import java.util.ArrayList;
import java.util.List;

import nu.xom.Attribute;
import nu.xom.Element;

public class VariablenNutzungSammeln extends BaumDurchsuchen{
    List<String> variablenNamen = new ArrayList<>();
    String[] attributeInAllenElementen = new String[] { "source","at","foreach"};

    public VariablenNutzungSammeln() {
       
    }

    private void sammeln(String werte) {
        String[] aWerte = werte.split("[ ,;]+");
        for(String wert : aWerte) {
            variablenNamen.add(wert);
        }
    }
    
    @Override
    protected void bearbeiteAttribut(Attribute attr) {
        if(hatEinenNamenAus(attr,attributeInAllenElementen)) {
            sammeln(attr.getValue());
        }
        if (istAttributInElement(attr,"SET","to")) {
            sammeln(attr.getValue());    
        
        }
        if (istAttributInElement(attr,"SQL","stmt")) {
            String[] vars = attr.getValue().split("\\?");
            for(int i=0;i < vars.length;i++) {
                if (i % 2 == 0) {
                    sammeln(vars[i]);    
                }
            }
        }
        super.bearbeiteAttribut(attr);
    }

    protected boolean istAttributInElement(Attribute attr,String elementName,String attributName) {
        return elementName.equals(((Element)attr.getParent()).getLocalName()) && attributName.equals(attr.getLocalName());
    }

    public List<String> getVariablenNamen() {
        return variablenNamen;
    }
}
