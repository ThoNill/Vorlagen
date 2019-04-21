package org.nill.modelle.xml;

import java.io.IOException;
import java.io.InputStream;

import nu.xom.Element;



public class IncludeElement extends Element {
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

	static public boolean hasTemplate(String name) {
		InputStream in = Thread.currentThread().getContextClassLoader()
				.getResourceAsStream(name);
		if (in != null) {
			try {
				in.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
			return true;
		}
		return false;
	}
}
