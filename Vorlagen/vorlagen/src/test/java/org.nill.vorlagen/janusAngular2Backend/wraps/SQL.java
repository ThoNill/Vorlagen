package janusAngular2Backend.wraps;

import java.util.ArrayList;
import java.util.List;

import org.nill.vorlagen.xml.st.WrapElement;

public class SQL extends WrapElement {

    public SQL() {
    }

    public List<String> getAbfrageParameter() {
        return getAbfrageParameter(getElem().getAttributeValue("stmt"));
    }

    public List<String> getAnzahlParameter() {
        String countStmt = getElem().getAttributeValue("stmtCount");
        if (countStmt != null) {
            return getAbfrageParameter(countStmt);
        } else {
            return getAbfrageParameter();
        }
    }

    public List<String> getAbfrageParameter(String stmt) {
        List<String> parameter = new ArrayList<>();
        String[] gesplitet = stmt.split("\\?");
        if (gesplitet.length > 1) {
            for (int i = 1; i < gesplitet.length; i += 2) {
                parameter.add(gesplitet[i].trim());
            }
        }
        return parameter;
    }

}
