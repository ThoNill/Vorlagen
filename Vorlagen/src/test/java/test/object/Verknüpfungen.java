package test.object;

import org.nill.object.Connection;
import org.nill.object.ObjectModell;

public class Verkn�pfungen extends ObjectModell {

	public Verkn�pfungen() {
		super();
	}

	public Connection nacha = new Connection("one2one",BeispielMandant.class,BeispielBuchung.class);
	public Connection nachb = new Connection("one2many",BeispielMandant.class,BeispielBuchung.class);
	public Connection nachc = new Connection("many2many",BeispielMandant.class,BeispielBuchung.class);

}
