package org.nill.vorlagen.xml.model;

import java.io.IOException;
import java.io.InputStream;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.nill.vorlagen.vorlagen.RuntimeVorlagenException;

import nu.xom.Element;



public class IncludeElement extends Element {
	static Logger logger = Logger.getLogger(IncludeElement.class.getSimpleName());

	private static int zaehler = 1;
	private String sternValue;
	private String filename;

	public IncludeElement(String name, String namespace,String filename) {
		super(name,namespace);
		this.filename = filename;
		inc();
	}



	private synchronized void inc() {
		sternValue = "" + zaehler;
		zaehler++;
	}

	public void replace() {
		InputStream in = Thread.currentThread().getContextClassLoader()
				.getResourceAsStream(filename);
		if (in != null) {
			Element template = File2Document.ladeDasRootElement(in);
			File2Document.ersetzeElementDurchTemplate(this, template, sternValue);
			
		}
	}

	public static boolean hasTemplate(String name) {
		InputStream in = Thread.currentThread().getContextClassLoader()
				.getResourceAsStream(name);
		if (in != null) {
			try {
				in.close();
			} catch (IOException e) {
				logger.log(Level.SEVERE, "Fehler in  hasTemplate");
				throw new RuntimeVorlagenException( "Error at hasTemplate",e);
				
			}
			return true;
		}
		return false;
	}
}
