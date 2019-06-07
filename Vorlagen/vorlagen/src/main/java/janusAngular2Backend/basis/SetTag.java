package janusAngular2Backend.basis;

import java.util.HashMap;

public class SetTag extends ChildTag {
    private String value;
    private CallTag call;

    
    public SetTag(PageBasis basis,String name,String value) {
        super(basis,name);
        this.value = value;
    }


    public String getValue() {
        return value;
    }


    @Override
    public Ergebnis execute(Parameter parameter) {
        HashMap<String,Object> r = new HashMap<String, Object>();
        r.put("name",getChildName());
        r.put("value",getValue());
        return new ErgebnisImpl(r);
    }


    public void setCall(CallTag call) {
       setParent(call);
       this.call = call;
        
    }
 

}
