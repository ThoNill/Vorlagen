package org.nill.vorlagen.st;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.stringtemplate.v4.ST;
import org.stringtemplate.v4.STGroupFile;
import org.stringtemplate.v4.StringRenderer;

public class STConsumerBasis<M> {
	static Logger logger = Logger.getLogger(STConsumerBasis.class.getSimpleName());

	protected Charset charset;

	public STConsumerBasis(Charset charset) {
		super();
		this.charset = charset;
	}

	protected void erzeugeAusgabe(M vorlageModell, String vorlageDatei, String ausgabeVerzeichnis) throws IOException  {
		STGroupFile group = createGroupFile(vorlageDatei);
		register(group);
		erzeugeAusgabeAusVorlageModell(group, ausgabeVerzeichnis, vorlageModell);
	}

	private STGroupFile createGroupFile(String vorlageDateiName) {
		/*URL url = Thread.currentThread().getContextClassLoader().getResource(vorlageDateiName);
		logger.log(Level.INFO,"vorgabe = " + vorlageDateiName);
		logger.log(Level.INFO,"url = " + url);
		logger.log(Level.INFO,"file " +url.getFile());
	*/
		return new STGroupFile(vorlageDateiName, '$', '$');
	}

	protected void register(STGroupFile group) {
		group.registerModelAdaptor(Object.class, new MethodNameAdapter());
		group.registerRenderer(String.class, new StringRenderer());
	}

	protected void erzeugeAusgabeAusVorlageModell(STGroupFile group, String ausgabeVerzeichnis, M vm)
			throws IOException{
		String dateiName = getAusgabe(group, ausgabeVerzeichnis, vm).toString();
		boolean überschreiben = isOverwrite(group, vm);
		boolean erzeugen = isCreate(group, vm);
		File d = new File(dateiName);
		logger.log(Level.INFO,"ausgabe: " + dateiName+" " +d.getAbsolutePath());

		if (erzeugen) {
			if (überschreiben || !d.exists()) {
				erzeugeEventuellFehlendeVerzeichnisse(dateiName);
				Writer writer = erzeugeWriter(dateiName);
				erzeugeAusgabe(group, writer, vm);
				writer.close();
			}
		} else {
			if (überschreiben && d.exists()) {
				Files.delete(d.toPath());
				if ( d.exists()) {
					throw new IllegalArgumentException("Konnte nicht gel�scht werden: "+ d.getAbsolutePath());
				}
			}
		}
	}

	private File getAusgabe(STGroupFile group, String ausgabeVerzeichnis, M modell) {
		return new File(ausgabeVerzeichnis, getDateiName(group, modell));
	}

	private String getDateiName(STGroupFile group, M modell) {
		return apply(group, "dateiName", modell);
	}

	private boolean isOverwrite(STGroupFile group, M modell) {
		return "true".equals(apply(group, "overwrite", modell));
	}

	private boolean isCreate(STGroupFile group, M modell) {
		return "true".equals(apply(group, "create", modell));
	}

	private void erzeugeEventuellFehlendeVerzeichnisse(String dateiName) throws IOException {
		File f = new File(dateiName);
		Files.createDirectories(f.getParentFile().toPath());
	}

	protected Writer erzeugeWriter(String dateName) throws FileNotFoundException {
		return new BufferedWriter(new OutputStreamWriter(new FileOutputStream(dateName), charset));
	}

	protected void erzeugeAusgabe(STGroupFile group, Writer writer, M modell) throws IOException {
		writer.write(apply(group, "dateiInhalt", modell));
		writer.flush();
	}

	private String apply(STGroupFile group, String templateName, M elem) {
		ST t = group.getInstanceOf(templateName);
		setzeSTModel(t, elem);
		return t.render();
	}

	protected void setzeSTModel(ST t, M elem) {
		t.add("model", elem);
	}

}