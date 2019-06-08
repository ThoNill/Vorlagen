package org.nill.vorlagen.generator;

import java.util.ArrayList;
import java.util.List;

public class GeneratorList implements Generator{
	List<Generator> generatoren = new ArrayList<>();

	@Override
	public void erzeugeAusgabe() {
		for(Generator g : generatoren) {
			g.erzeugeAusgabe();
		}
	}
	
	public void add(Generator g) {
		generatoren.add(g);
	}
	
	
}