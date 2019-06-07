package janusAngular2Backend.basis;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class PageBasis {
    List<BasisTag> handlerArray = new ArrayList<>();
    HashMap<String, BasisTag> handlerMap = new HashMap<>();

    public PageBasis() {

    }

    public PageBasis add(BasisTag e) {
        handlerArray.add(e);
        return this;
    }

    public PageBasis remove(Object o) {
        handlerArray.remove(o);
        return this;
    }

    public void init() {
        for (BasisTag handler : handlerArray) {
            String name = handler.getName();
            handlerMap.put(name, handler);
        }

    }

    public Ergebnis execute(String name, Parameter parameter) {
        try {
            return handlerMap.get(name).execute(parameter);
        } catch (Exception e) {
            return new ErgebnisImpl(e);
        }
    }

}
