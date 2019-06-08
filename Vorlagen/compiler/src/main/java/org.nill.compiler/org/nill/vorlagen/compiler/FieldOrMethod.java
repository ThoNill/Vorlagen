package org.nill.vorlagen.compiler;

import java.util.List;

import org.nill.vorlagen.interfaces.Parameter;

public interface FieldOrMethod {
		String getKind();
		ClazzValue getClazz();
		String getDocumentation();
		String getName();
		String getInitialization();
		String getAnnotations();
		ClazzValue getSource();
		ClazzValue getTarget();
		ClazzValue getReturn();
		List<Parameter> getParameter();
	
}
