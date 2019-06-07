package janusAngular2Backend.basis;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class CallTag extends ChildTag {
    private String command;
    private BeanTag bean;
    private List<SetTag> setTag = new ArrayList<>();
    
    public CallTag(PageBasis basis,String name,String command) {
        super(basis,name);
        this.command = command;
        }


    public BeanTag getBean() {
        return bean;
    }


    public void setBean(BeanTag bean) {
        setParent(bean);
        this.bean = bean;
    }
    
    public CallTag add(SetTag set) {
        setTag.add(set);
        set.setCall(this);
        return this;
    }


    @Override
    public Ergebnis execute(Parameter parameter) {
        HashMap<String,Object> p = new HashMap<>();
        for(SetTag set : setTag) {
            Ergebnis e = set.execute(parameter);
            p.put((String)e.get("name"),e.get("value"));
        }
        p.put("command",command);
        return bean.execute(new ParameterImpl(p));
    }
 

}
