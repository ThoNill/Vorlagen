package org.nill.vorlagen;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;

public abstract class FunctionFabrik<MODELL, VORLAGEN_MODELL,VORLAGEN_BESCHREIBUNG> implements VorlagenFabrik<MODELL, VORLAGEN_MODELL,VORLAGEN_BESCHREIBUNG> {
	private Function<MODELL,VORLAGEN_MODELL> transformation;
	
	public FunctionFabrik(Function<MODELL, VORLAGEN_MODELL> transformation) {
		super();
		this.transformation = transformation;
	}

	@Override
	public List<VORLAGEN_MODELL> erzeugeVorlagenModell(MODELL modell) throws Exception
{
		List<VORLAGEN_MODELL> liste = new ArrayList<>();
		liste.add(transformation.apply(modell));
		return liste;
	}
}
