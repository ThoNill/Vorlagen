package test;

import org.nill.vorlagen.compiler.model.ObjectModell;
import org.nill.vorlagen.compiler.util.Connection;

import test.object.Adresse;
import test.object.AdresseTelefon;
import test.BeispielBuchung;
import test.BeispielMandant;

public class Verknuepfungen extends ObjectModell {


	public Connection at = new Connection("one2one",BeispielMandant.class,AdresseTelefon.class);
	public Connection a = new Connection("one2many",BeispielMandant.class,Adresse.class);

	public Connection nacha = new Connection("one2one",BeispielMandant.class,BeispielBuchung.class);
	public Connection nachb = new Connection("one2many",BeispielMandant.class,BeispielBuchung.class);
	public Connection nachc = new Connection("many2many",BeispielMandant.class,BeispielBuchung.class);

	public Verknuepfungen() {
		super();
	}
}
