package org.nill.reactive;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.nio.charset.Charset;
import java.nio.file.Files;
import org.reactivestreams.Subscriber;
import org.stringtemplate.v4.ST;
import org.stringtemplate.v4.STGroupFile;
import org.stringtemplate.v4.StringRenderer;
import reactor.core.publisher.BaseSubscriber;
import javax.lang.model.element.Element;
import javax.lang.model.element.TypeElement;


public class STConsumerBasis<M>  {
	protected Charset charset;

	public STConsumerBasis(Charset charset) {
		super();
		this.charset = charset;
	}

	

	protected void erzeugeAusgabe(M vorlageModell, File vorlageDatei, File ausgabeVerzeichnis) throws Exception {
		STGroupFile group = createGroupFile(vorlageDatei);
		register(group);
		erzeugeAusgabeAusVorlageModell(group,ausgabeVerzeichnis,vorlageModell);
	}



	private STGroupFile createGroupFile(File vorlageDatei) {
		return new STGroupFile(vorlageDatei.toString(),'$','$');
	}

	protected void register(STGroupFile group) {
		group.registerModelAdaptor(Element.class,new ElementAdapter());
		group.registerRenderer(String.class, new StringRenderer());
	}

	protected void erzeugeAusgabeAusVorlageModell(STGroupFile	group, File ausgabeVerzeichnis, M vm)
			throws IOException, FileNotFoundException {
				String dateiName = getAusgabe(group,ausgabeVerzeichnis,vm).toString();
				boolean überschreiben = isOverwrite(group, vm);
				if (überschreiben || !(new File(dateiName)).exists()) {
					erzeugeEventuellFehlendeVerzeichnisse(dateiName);
					Writer writer = erzeugeWriter(dateiName);
					erzeugeAusgabe(group,writer, vm);
					writer.close();
				}
			}

	private File getAusgabe(STGroupFile	group, File ausgabeVerzeichnis, M modell) {
		return new File(ausgabeVerzeichnis,getDateiName(group,modell));
	}

	private String getDateiName(STGroupFile	group, M modell) {
	    return apply(group,"dateiName",modell);
	}
	
	private boolean isOverwrite(STGroupFile	group, M modell) {
	    return "true".equals(apply(group,"overwrite",modell));
	}

	private void erzeugeEventuellFehlendeVerzeichnisse(String dateiName) throws IOException {
		File f = new File(dateiName);
		Files.createDirectories(f.getParentFile().toPath());
	}

	protected Writer erzeugeWriter(String dateName) throws FileNotFoundException {
		return new BufferedWriter(new OutputStreamWriter(new FileOutputStream(dateName), charset));
	}

	protected void erzeugeAusgabe(STGroupFile	group, Writer writer, M modell) throws IOException {
	    writer.write(apply(group,"dateiInhalt",modell));
	    writer.flush();
	}

	private String apply(STGroupFile	group, String templateName, M elem) {
		ST t = group.getInstanceOf(templateName);
		setzeSTModel(t,elem);
		return t.render();
	}

	protected void setzeSTModel(ST t, M elem) {
	    t.add("urmodell", elem);
	 }

}