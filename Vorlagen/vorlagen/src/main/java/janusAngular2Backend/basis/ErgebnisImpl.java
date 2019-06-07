package janusAngular2Backend.basis;

import java.util.HashMap;

public class ErgebnisImpl extends ParameterImpl implements Ergebnis{

    private int errorCode;
    
    public ErgebnisImpl(HashMap<String, Object> map) {
        super(map);
        this.errorCode=0;
    }
 
    public ErgebnisImpl(int errorCode) {
        super(new HashMap<String, Object>());
        this.errorCode=errorCode;
    }

    public ErgebnisImpl(Exception ex) {
        this(1);
        put("exception",ex);
        this.errorCode=errorCode;
    }

    
    
    @Override
    public int errorCode() {
        return errorCode;
    }

    @Override
    public boolean isOk() {
        return errorCode==0;
    }

}
