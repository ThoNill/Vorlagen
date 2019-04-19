package org.nill.ST.vorlagen;

import java.io.File;
import java.io.IOException;
import java.io.Writer;
import java.nio.charset.Charset;

import org.nill.vorlagen.Vorlage;
import org.nill.vorlagen.VorlagenFabrik;
import org.stringtemplate.v4.ST;
import org.stringtemplate.v4.STGroupFile;
import org.stringtemplate.v4.StringRenderer;

public class STVorlage<MODELL, VORLAGEN_MODELL> extends Vorlage<MODELL,VORLAGEN_MODELL,File>{
	protected STGroupFile group = null;
	private File basisVerzeichnis;
	
	

	public STVorlage(VorlagenFabrik<MODELL, VORLAGEN_MODELL,File> vorlagenFabrik,Charset charset,String dateiName,File basisVerzeichnis,boolean überschreiben) {
		super(vorlagenFabrik,charset,überschreiben);
		this.basisVerzeichnis = basisVerzeichnis;
		group = new STGroupFile(dateiName,'$','$');
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

    public String getDateiName(VORLAGEN_MODELL modell) {
        return apply("dateiName",modell);
    }    
    
  	@Override
	public File getAusgabe(VORLAGEN_MODELL modell) {
		return new File(basisVerzeichnis,getDateiName(modell));
	}  
}
