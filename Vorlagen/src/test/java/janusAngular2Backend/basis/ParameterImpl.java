package janusAngular2Backend.basis;

import java.util.HashMap;

public class ParameterImpl implements Parameter {
    HashMap<String,Object> map;

    public ParameterImpl(HashMap<String, Object> map) {
        super();
        this.map = map;
    }
    
    public Object get(String name) {
        return map.get(name);
    }
    
    protected void put(String name,Object obj) {
        map.put(name,obj);
    }
 
}
