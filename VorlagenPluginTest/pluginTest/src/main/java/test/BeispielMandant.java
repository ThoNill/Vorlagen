package test;

import java.time.LocalDate;
import java.util.List;

import org.nill.vorlagen.compiler.model.ObjectModell;
import org.nill.vorlagen.object.ddd.Aggregate;
import org.nill.vorlagen.object.ddd.Entity;

import test.object.BeispielEnumeration;
import test.object.MonatJahr;



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
		addConnection(new Verknuepfungen());
	}

	
}
