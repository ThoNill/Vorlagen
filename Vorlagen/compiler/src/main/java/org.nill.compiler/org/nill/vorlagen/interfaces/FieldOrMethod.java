package org.nill.vorlagen.interfaces;

import java.util.List;

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
