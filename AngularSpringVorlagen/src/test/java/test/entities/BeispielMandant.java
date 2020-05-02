package test.entities;

import org.nill.vorlagen.compiler.model.ObjectModell;
import org.nill.vorlagen.object.ddd.Aggregate;
import org.nill.vorlagen.object.ddd.Entity;

public class BeispielMandant extends ObjectModell implements Entity, Aggregate{
	
	public String name = "name";
	public int count;
	
	public BeispielMandant() throws Exception {
		super();
		addConnection(new Verknüpfungen());
	}

}
