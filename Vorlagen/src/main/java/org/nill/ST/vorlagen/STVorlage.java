package org.nill.ST.vorlagen;

import java.io.File;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.Writer;
import java.nio.file.Files;
import org.nill.vorlagen.Vorlage;
import org.stringtemplate.v4.ST;
import org.stringtemplate.v4.STGroupFile;

public class STVorlage<VORLAGEN_MODELL> implements Vorlage<VORLAGEN_MODELL>{
	protected STGroupFile group = null;
	

	public STVorlage(String dateiName) {
		group = new STGroupFile(dateiName +".stg",'$','$');
	}
	
	public String apply(String templateName,Object elem) {
		ST t = group.getInstanceOf(templateName);
		t.add("x", elem);
		return t.render();
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
