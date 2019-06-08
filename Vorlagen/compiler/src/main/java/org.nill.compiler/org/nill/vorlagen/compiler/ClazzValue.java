package org.nill.vorlagen.compiler;

import java.util.Collection;

import javax.persistence.Embeddable;

import org.nill.vorlagen.object.ddd.Aggregate;
import org.nill.vorlagen.object.ddd.Entity;
import org.nill.vorlagen.object.ddd.Repository;
import org.nill.vorlagen.object.ddd.Service;
import org.nill.vorlagen.object.ddd.Value;

public class ClazzValue {

	private Class<?> clazz;
	private Class<?> converterClass;
	private String typeDefinition;

	public ClazzValue(Class<?> clazz, Class<?> converterClass, String typeDefinition) {
		super();
		this.clazz = clazz;
		this.converterClass = converterClass;
		this.typeDefinition = typeDefinition;
	}

	public boolean isWithConverter() {
		return converterClass != null;
	}

	public String getTypeDefinition() {
		return typeDefinition;
	}

	public String getArrayTypeDefinition() {
		return typeDefinition.substring(0, typeDefinition.lastIndexOf('['));
	}

	public String getArrayListTypeDefinition() {
		String arrayType = getArrayTypeDefinition();
		switch (arrayType) {
		case "int":
			return "Integer";
		case "long":
			return "Long";
		case "byte":
			return "Byte";
		case "char":
			return "Char";
		default:
			return arrayType;
		}
	}

	public String getCollectionTypeDefinition() {
		return typeDefinition.substring(typeDefinition.indexOf('<') + 1, typeDefinition.lastIndexOf('>'));
	}

	public ClazzValue getConverterClass() {
		return new ClazzValue(converterClass, null, converterClass.getCanonicalName());
	}

	public void setClazz(Class<?> clazz) {
		this.clazz = clazz;
	}

	public Class<?> getClazz() {
		return clazz;
	}

	public String getSimpleName() {
		return clazz.getSimpleName();
	}

	public String getCanonicalName() {
		return clazz.getCanonicalName();
	}

	public boolean isValue() {
		return Value.class.isAssignableFrom(clazz);
	}

	public boolean isEntity() {
		return Entity.class.isAssignableFrom(clazz);
	}


	public boolean isAggregate() {
		return Aggregate.class.isAssignableFrom(clazz);
	}

	public boolean isService() {
		return Service.class.isAssignableFrom(clazz);
	}

	public boolean isRepository() {
		return Repository.class.isAssignableFrom(clazz);
	}

	public boolean isEnum() {
		return clazz.isEnum();
	}

	public boolean isEmbedabble() {
		return clazz.isAnnotationPresent(Embeddable.class);
	}

	public boolean isCollection() {
		return Collection.class.isAssignableFrom(clazz);
	}

	public boolean isArray() {
		return clazz.isArray();
	}

	public boolean isPrimitiveType() {
		return clazz.isPrimitive();
	}

	public boolean isObjectModell() {
		return ObjectModell.class.isAssignableFrom(clazz);
	}
}
