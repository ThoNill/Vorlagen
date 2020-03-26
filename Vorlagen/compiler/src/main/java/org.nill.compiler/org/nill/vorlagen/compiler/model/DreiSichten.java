package org.nill.vorlagen.compiler.model;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.OffsetDateTime;
import java.time.OffsetTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import javax.lang.model.element.Element;
import javax.lang.model.element.ElementKind;

import org.nill.vorlagen.compiler.markerClasses.ByteBLOB;
import org.nill.vorlagen.compiler.markerClasses.CharBLOB;
import org.nill.vorlagen.compiler.util.Parameter;

import com.sun.source.tree.MethodTree;
import com.sun.source.tree.Tree;
import com.sun.source.tree.VariableTree;

public class DreiSichten implements FieldOrMethod {
	private Element element;
	private Tree tree;
	private Object reflektiv;
	private String documentation;
	private Class<?> converterClass;

	public DreiSichten(Element element, Tree tree, Object reflektiv, String documentation, Class<?> converterClass) {
		super();
		this.element = element;
		this.tree = tree;
		this.reflektiv = reflektiv;
		this.documentation = (documentation == null) ? "" : documentation.trim();
		this.converterClass = converterClass;
	}

	public DreiSichten(Element element, Tree tree, Object reflektiv, String documentation) {
		this(element, tree, reflektiv, documentation, null);
	}

	public Element getElement() {
		return element;
	}

	public Tree getTree() {
		return tree;
	}

	public Object getReflektiv() {
		return reflektiv;
	}

	public String getDocumentation() {
		return documentation;
	}

	@Override
	public String toString() {
		return "ConnectionSicht [element=" + element + ", tree=" + tree + ", reflektiv=" + reflektiv
				+ ", documentation=" + documentation + "]";
	}

	@Override
	public String getKind() {
		Class<?>[] classes = { LocalDate.class, LocalDateTime.class, LocalTime.class, OffsetDateTime.class,
				OffsetTime.class,ByteBLOB.class,CharBLOB.class };
		for (Class<?> c : classes) {
			Optional<String> s = extraClass(c);
			if (s.isPresent()) {
				return s.get();
			}
		}
		if (getClazz().isEnum()) {
			return "enumeration";
		}
		return "value" + ((getClazz().isCollection()) ? "Collection" : "") + ((getClazz().isArray()) ? "Array" : "");
	}

	private Optional<String> extraClass(Class c) {
		if (c.isAssignableFrom(getClazz().getClazz())) {
			return Optional.of(c.getSimpleName());
		}
		return Optional.empty();
	}

	@Override
	public ClazzValue getClazz() {
		if (element.getKind().equals(ElementKind.FIELD)) {
			VariableTree v = (VariableTree) tree;
			Field f = (Field) reflektiv;
			return new ClazzValue(f.getType(), converterClass, v.getType().toString());
		}
		return null;
	}

	@Override
	public String getName() {
		return element.getSimpleName().toString();
	}

	@Override
	public String getInitialization() {
		if (element.getKind().equals(ElementKind.FIELD)) {
			VariableTree v = (VariableTree) tree;
			return v.getInitializer().toString();
		}
		return "";
	}

	@Override
	public String getAnnotations() {
		return element.getAnnotationMirrors().toString();
	}

	@Override
	public ClazzValue getSource() {
		return null;
	}

	@Override
	public ClazzValue getTarget() {
		return null;
	}

	@Override
	public ClazzValue getReturn() {
		if (element.getKind().equals(ElementKind.METHOD)) {
			MethodTree v = (MethodTree) tree;
			Method m = (Method) reflektiv;
			return new ClazzValue(m.getReturnType(), null, v.getReturnType().toString());
		}
		return null;
	}

	@Override
	public List<Parameter> getParameter() {
		if (element.getKind().equals(ElementKind.METHOD)) {
			MethodTree mt = (MethodTree) tree;
			Method m = (Method) reflektiv;
			List<Parameter> list = new ArrayList<>();
			for (VariableTree v : mt.getParameters()) {
				list.add(new Parameter(v.getType().toString(), v.getName().toString(),
						Arrays.toString(m.getAnnotatedParameterTypes())));
			}
			return list;
		}
		return Collections.emptyList();

	}

}
