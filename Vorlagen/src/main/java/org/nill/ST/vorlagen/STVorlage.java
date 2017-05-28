package org.nill.ST.vorlagen;

import java.io.IOException;
import java.io.Writer;

import org.nill.vorlagen.Vorlage;
import org.stringtemplate.v4.ST;
import org.stringtemplate.v4.STGroupFile;
import org.stringtemplate.v4.StringRenderer;

public class STVorlage<VORLAGEN_MODELL> implements Vorlage<VORLAGEN_MODELL>{
	protected STGroupFile group = null;
	

	public STVorlage(String dateiName) {
		group = new STGroupFile(dateiName +".stg",'$','$');
		group.registerRenderer(String.class, new StringRenderer());
	}
	
	public String apply(String templateName,VORLAGEN_MODELL elem) {
		ST t = group.getInstanceOf(templateName);
		setzeSTModel(t,elem);
		return t.render();
	}

    protected void setzeSTModel(ST t,VORLAGEN_MODELL elem) {
        t.add("urmodell", elem);
    }

    @Override
    public void erzeugeAusgabe(Writer writer, VORLAGEN_MODELL modell)
            throws IOException {
        writer.write(apply("dateiInhalt",modell));
        writer.flush();
        
    }

    @Override
    public String getPfadMitDateiName(VORLAGEN_MODELL modell) {
        return apply("dateiName",modell);
    }    
}
