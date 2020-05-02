package test.entities;

import org.nill.vorlagen.compiler.model.ObjectModell;
import org.nill.vorlagen.compiler.util.Connection;

public class Verknüpfungen extends ObjectModell {

	public Verknüpfungen() {
		super();
	}

	public Connection nacha = new Connection("one2one",BeispielMandant.class,BeispielBuchung.class);
	public Connection nachb = new Connection("one2many",BeispielMandant.class,BeispielBuchung.class);
	public Connection nachc = new Connection("many2many",BeispielMandant.class,BeispielBuchung.class);

}
