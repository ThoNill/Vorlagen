package janusAngular2Backend.basis;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class SqlTag extends BasisTag {
    private String stmt;
    List<ColumnTag> columnTag = new ArrayList<>();

    public SqlTag(PageBasis basis, String name, String stmt) {
        super(basis, name);
        this.stmt = stmt;
    }
    
    

    public SqlTag add(ColumnTag column) {
        columnTag.add(column);
        column.setSql(this);
        return this;
    }
    
    public List<Object> createList(Parameter parameter) {
        return new ArrayList<Object>();
    }

    @Override
    public Ergebnis execute(Parameter parameter) {
        HashMap<String, Object> map = new HashMap<>();
        map.put("parameter", parameter);
        map.put("result", createList(parameter));
        return new ErgebnisImpl(map);
    }

}
