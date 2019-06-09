package org.nill.vorlagen.compiler.model;

import java.util.ArrayList;
import java.util.List;

import javax.lang.model.element.PackageElement;
import javax.lang.model.util.Elements;

import com.sun.source.tree.ImportTree;

public class ClassVisitorData {
	private Class clazz;
	private FieldOrMethod classSicht;
	private List<ImportTree> importsTrees = new ArrayList<>();
	private List<FieldOrMethod> methodSichten  = new ArrayList<>();
	private List<FieldOrMethod> fieldSichten  = new ArrayList<>();
	private Elements elements;
	private PackageElement packageElement;

	public ClassVisitorData() {
		super();
	}

	public ClazzValue getClazzValue() {
		return new ClazzValue(clazz,null,"");
	}

	public Class<?> getClazz() {
		return clazz;
	}
	public void setClazz(Class clazz) {
		this.clazz = clazz;
	}

	public FieldOrMethod getClassSicht() {
		return classSicht;
	}

	public void setClassSicht(DreiSichten classSicht) {
		this.classSicht = classSicht;
	}

	public List<ImportTree> getImportsTrees() {
		return importsTrees;
	}

	public List<FieldOrMethod> getMethodSichten() {
		return methodSichten;
	}


	public List<FieldOrMethod> getFieldSichten() {
		return fieldSichten;
	}



	public Elements getElements() {
		return elements;
	}

	public void setElements(Elements elements) {
		this.elements = elements;
	}

	public PackageElement getPackageElement() {
		return packageElement;
	}

	public void setPackageElement(PackageElement packageElement) {
		this.packageElement = packageElement;
	}
}
