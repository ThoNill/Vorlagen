package test.entities;

import org.nill.vorlagen.compiler.model.ObjectModell;
import org.nill.vorlagen.object.ddd.Entity;

public class BeispielBuchung extends ObjectModell implements Entity {
	
	public String name = "name";
	public int count;
	
	public BeispielBuchung() throws Exception {
		super();
		addConnection(new Verknüpfungen());
	}

}
