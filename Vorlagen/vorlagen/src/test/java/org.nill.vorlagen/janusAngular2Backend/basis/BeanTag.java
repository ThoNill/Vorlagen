package janusAngular2Backend.basis;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class BeanTag extends BasisTag {
    private String name;
    private Class clazz;
    private Object instanceOfClazz;
    List<CallTag> callArray = new ArrayList<>();  
  
    
    public BeanTag(PageBasis basis,String name,Class clazz) {
        super(basis,name);
        this.clazz = clazz;
    }
    
    public BeanTag add(CallTag call) {
       callArray.add(call);
       call.setBean(this);
       return this;
    }

    public CallTag getCall(int index) {
        return callArray.get(0);
    }

    @Override
    public Ergebnis execute(Parameter parameter) {
        HashMap<String, Object> map = new HashMap<>();
        map.put("parameter", parameter);
        return new ErgebnisImpl(map);
    }
 

}
