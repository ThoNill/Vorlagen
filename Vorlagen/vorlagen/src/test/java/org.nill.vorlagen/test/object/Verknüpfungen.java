package test.object;

import org.nill.vorlagen.object.Connection;
import org.nill.vorlagen.object.ObjectModell;

public class Verkn�pfungen extends ObjectModell {

	public Verkn�pfungen() {
		super();
	}

	public Connection at = new Connection("one2one",BeispielMandant.class,AdresseTelefon.class);
	public Connection a = new Connection("one2many",BeispielMandant.class,Adresse.class);

	public Connection nacha = new Connection("one2one",BeispielMandant.class,BeispielBuchung.class);
	public Connection nachb = new Connection("one2many",BeispielMandant.class,BeispielBuchung.class);
	public Connection nachc = new Connection("many2many",BeispielMandant.class,BeispielBuchung.class);

}
