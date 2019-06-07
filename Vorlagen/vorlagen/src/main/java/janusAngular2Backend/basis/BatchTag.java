package janusAngular2Backend.basis;

import java.util.HashMap;
import java.util.List;

public class BatchTag extends ChildTag {
    private BeanTag onStartBean;
    private BeanTag onRunBean;
    private BeanTag onStopBean;
    private SqlTag sql;
    
    public BatchTag(PageBasis basis,String name) {
        super(basis,name);
    }

    public BatchTag add(SqlTag sql) {
        this.sql = sql;
        return this;
    }

    public Adder getStart() {
        final BatchTag batch = this;
        return new Adder() {
            @Override
			public BatchTag add(BeanTag bean) {
                batch.setStart(bean);
                return batch;
            }
        };
    }

    public Adder getRun() {
        final BatchTag batch = this;
        return new Adder() {
            @Override
			public BatchTag add(BeanTag bean) {
                batch.setRun(bean);
                return batch;
            }
        };
    }

    public Adder getStop() {
        final BatchTag batch = this;
        return new Adder() {
            @Override
			public BatchTag add(BeanTag bean) {
                batch.setStop(bean);
                return batch;
            }
        };
    }
    
    
    public BatchTag setStart(BeanTag bean) {
        onStartBean = bean;
        return this;
    }

    public BatchTag setRun(BeanTag bean) {
        onRunBean = bean;
        return this;
    }

    public BatchTag setStop(BeanTag bean) {
        onStopBean = bean;
        return this;
    }

    @Override
    public Ergebnis execute(Parameter parameter) {
        onStartBean.getCall(0).execute(parameter);
        List<Object> list = sql.createList(parameter);
        int count = 0;
        for(Object o : list) {
            onRunBean.getCall(0).execute(parameter);
            count++;
        }
        onStopBean.getCall(0).execute(parameter);
        HashMap<String,Object> p = new HashMap<>();
        p.put("count",count);
        return new ErgebnisImpl(p);
    }

    @FunctionalInterface
    public interface Adder {
        BatchTag add(BeanTag bean);
    }

}
