package org.nill.generator;

import java.util.ArrayList;
import java.util.List;

public class GeneratorList implements Generator{
	List<Generator> generatoren = new ArrayList<>();

	@Override
	public void erzeugeAusgabe() throws Exception {
		for(Generator g : generatoren) {
			g.erzeugeAusgabe();
		}
	}
	
	public void add(Generator g) {
		generatoren.add(g);
	}
	
	
}