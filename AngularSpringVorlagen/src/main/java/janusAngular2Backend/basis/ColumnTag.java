package janusAngular2Backend.basis;

public class ColumnTag extends ChildTag {
    private String command;
    private SqlTag sql;
    
    public ColumnTag(PageBasis basis,String name) {
        super(basis,name);
        
        }


    public SqlTag getBean() {
        return sql;
    }


    public void setSql(SqlTag sql) {
        setParent(sql);
        this.sql = sql;
    }


    @Override
    public Ergebnis execute(Parameter parameter) {
        return null;
    }
 

}
