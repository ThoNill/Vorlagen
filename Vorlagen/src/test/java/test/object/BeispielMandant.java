package test.object;

import org.nill.object.ObjectModell;

public class BeispielMandant extends ObjectModell {
	
	public String name = "name";
	public int count;
	
	public BeispielMandant() throws Exception {
		super();
		addConnection(new Verknüpfungen());
	}

}
