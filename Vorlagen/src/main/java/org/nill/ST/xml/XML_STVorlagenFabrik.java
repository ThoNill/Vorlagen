package org.nill.ST.xml;

import java.io.File;
import java.nio.charset.StandardCharsets;
import java.util.function.Function;
import org.nill.ST.vorlagen.STVorlagenFabrik;
import org.nill.vorlagen.Vorlage;
import nu.xom.Document;

public class XML_STVorlagenFabrik extends STVorlagenFabrik<Document, Document> {
	private String packageName;
	private String defaultClass;

	public XML_STVorlagenFabrik(File verzeichnisBeschreibung, File basisAusgabeVerzeichnis, String packageName,
			String defaultClass, boolean überschreiben) {
		this(verzeichnisBeschreibung, basisAusgabeVerzeichnis, packageName, defaultClass, überschreiben,
				Function.identity());
	}

	public XML_STVorlagenFabrik(File verzeichnisBeschreibung, File basisAusgabeVerzeichnis, String packageName,
			String defaultClass, boolean überschreiben, Function<Document, Document> transformation) {
		super(transformation, basisAusgabeVerzeichnis, überschreiben);
		this.packageName = packageName;
		this.defaultClass = defaultClass;
	}

	@Override
	public Vorlage<Document, Document, File> erzeugeVorlage(File beschreibung) {
		return new XML_STVorlage(this, StandardCharsets.UTF_8, beschreibung.toString(), getAusgabeVerzeichnis(),
				packageName, defaultClass, überschreiben);
	}

}