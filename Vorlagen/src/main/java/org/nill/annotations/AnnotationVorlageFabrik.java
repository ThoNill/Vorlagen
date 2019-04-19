package org.nill.annotations;

import java.io.File;
import java.nio.charset.StandardCharsets;
import java.util.function.Function;

import org.nill.ST.vorlagen.STVorlage;
import org.nill.vorlagen.FunctionFabrik;
import org.nill.vorlagen.Vorlage;

public class AnnotationVorlageFabrik<MODELL, VORLAGEN_MODELL>
		extends FunctionFabrik<MODELL, VORLAGEN_MODELL, File> {

	public AnnotationVorlageFabrik(Function<MODELL, VORLAGEN_MODELL> transformation) {
		super(transformation);
	}

	@Override
	public Vorlage<MODELL, VORLAGEN_MODELL, File> erzeugeVorlage(File beschreibung) {
		return new STVorlage<MODELL, VORLAGEN_MODELL>(this, StandardCharsets.UTF_8, beschreibung.toString(),
				new File(""), true);
	}
}
