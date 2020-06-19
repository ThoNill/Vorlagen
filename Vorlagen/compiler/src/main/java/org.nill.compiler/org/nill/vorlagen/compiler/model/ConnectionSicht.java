package org.nill.vorlagen.compiler.model;

import java.util.Collections;
import java.util.List;

import javax.lang.model.element.Element;

import org.nill.vorlagen.compiler.util.Connection;
import org.nill.vorlagen.compiler.util.Parameter;

import com.sun.source.tree.Tree;

public class ConnectionSicht implements FieldOrMethod{
	private String kind;
	private String name;
	private Connection connection;
	

	
	public ConnectionSicht( String kind, String name, Connection connection) {
		super();
		this.kind = kind;
		this.name = name;
		this.connection = connection;
	}

	public Element getElement() {
		return null;
	}

	public Tree getTree() {
		return null;
	}

	public Object getReflektiv() {
		return null;
	}


	public String getDocumentation() {
		return "";
	}



	@Override
	public String getKind() {
		if(getTarget().isEmbedabble()) {
			return kind + "Embedabble";
		}
		return kind;
	}


	@Override
	public ClazzValue getClazz() {
		return new ClazzValue(connection.getTarget(),null,null);
	}


	@Override
	public String getName() {
			return name;
	}

	public String getConnectionName() {
		if (name.startsWith("_")) {
			return getSource().getSimpleName();
		} else {
			return name;
		}
	}

	
	public String getSourceName() {
		if (name.startsWith("_")) {
			return getSource().getSimpleName();
		} else {
			return name + "_" + getSource().getSimpleName();
		}
	}

	public String getTargetName() {
		if (name.startsWith("_")) {
			return getTarget().getSimpleName();
		} else {
			return name + "_" + getTarget().getSimpleName();
		}
	}

	
	@Override
	public String getInitialization() {
		return "";
	}


	@Override
	public String getAnnotations() {
		return "";
	}


	@Override
	public ClazzValue getSource() {
		return new ClazzValue(connection.getSource(),null,null);
	}

	@Override
	public ClazzValue getTarget() {
		return new ClazzValue(connection.getTarget(),null,null);
	}

	@Override
	public ClazzValue getReturn() {
		return null;
	}


	@Override
	public List<Parameter> getParameter() {
		return Collections.emptyList();
	
	}



}
