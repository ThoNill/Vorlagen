package test.object;

import org.nill.object.ObjectModell;

public class BeispielBuchung extends ObjectModell {
	
	public String name = "name";
	public int count;
	
	public BeispielBuchung() throws Exception {
		super();
		addConnection(new Verknüpfungen());
	}

}
