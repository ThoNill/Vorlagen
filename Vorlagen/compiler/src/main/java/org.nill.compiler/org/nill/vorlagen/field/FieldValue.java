package org.nill.vorlagen.field;

import javax.persistence.Embeddable;

import org.nill.vorlagen.object.ddd.Aggregate;
import org.nill.vorlagen.object.ddd.Entity;
import org.nill.vorlagen.object.ddd.Repository;
import org.nill.vorlagen.object.ddd.Service;
import org.nill.vorlagen.object.ddd.Value;

public class FieldValue extends WithKind {
	
	private Class<?> clazz;
	private Object initialisation;
	private int guiLenght;
	private String checkRegexp;
	private Class<?> converterClass;
	private String guiLayout;

	public FieldValue(String kind, Class<?> clazz, Object initialisation) {
		super(kind);
		this.clazz = clazz;
		this.initialisation = initialisation;
	}

	public void setClazz(Class<?> clazz) {
		this.clazz = clazz;
	}

	public void setInitialisation(Object initialisation) {
		this.initialisation = initialisation;
	}

	public void setGuiLenght(int guiLenght) {
		this.guiLenght = guiLenght;
	}

	public void setCheckRegexp(String checkRegexp) {
		this.checkRegexp = checkRegexp;
	}

	public void setConverterClass(Class<?> converterClass) {
		this.converterClass = converterClass;
	}

	public void setGuiLayout(String guiLayout) {
		this.guiLayout = guiLayout;
	}

	public Class<?> getClazz() {
		return clazz;
	}

	public Object getInitialisation() {
		return initialisation;
	}
	
	boolean isWithCOnverter() {
		return converterClass != null;
	}
	
	boolean isWithLenght() {
		return guiLenght >0;
	}
	
	boolean isWithGuiPattern() {
		return guiLayout != null;
	}
	
	boolean isWithCheckRegexp() {
		return checkRegexp !=null;
	}
	
	boolean isValue() {
		return Value.class.isAssignableFrom(clazz);
	}
	
	boolean isEntity() {
		return Entity.class.isAssignableFrom(clazz);
	}
	
	boolean isAggregate() {
		return Aggregate.class.isAssignableFrom(clazz);
	}
	
	boolean isService() {
		return Service.class.isAssignableFrom(clazz);
	}
	
	boolean isRepository() {
		return Repository.class.isAssignableFrom(clazz);
	}
	
	boolean isEmbedabble() {
		return clazz.isAnnotationPresent(Embeddable.class);
	}
	
}
