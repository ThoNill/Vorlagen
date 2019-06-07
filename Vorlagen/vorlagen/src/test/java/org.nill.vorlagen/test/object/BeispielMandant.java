package test.object;

import java.time.LocalDate;
import java.util.List;

import org.nill.vorlagen.object.ObjectModell;
import org.nill.vorlagen.object.ddd.Aggregate;
import org.nill.vorlagen.object.ddd.Entity;



public class BeispielMandant extends ObjectModell implements Entity, Aggregate{
	
	/**
	 erstens
	 */
	public String name = "name";
	
	@Deprecated
	public int count;
	public MonatJahr mj;
	
	public List<Integer> nummern;
	
	public int[] anzahl;
	
	public BeispielEnumeration benum;
	
	public LocalDate geburtstag;
	
	public BeispielMandant() throws Exception {
		super();
		addConnection(new Verknüpfungen());
	}

	
}
