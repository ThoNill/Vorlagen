package org.nill.vorlagen.object;

import java.lang.reflect.Field;
import java.util.Iterator;

import org.nill.vorlagen.compiler.ClassVisitorData;
import org.nill.vorlagen.interfaces.FieldOrMethod;

public class ObjectModell extends ClassVisitorData {

	public ObjectModell() {
		super();
	}

	public void addConnection(String name, Connection con) {
		if (this.getClazz().equals(con.getSource())) {
			getFieldSichten().add(new ConnectionSicht("from" + con.getKind(), name, con));
		}
		if (this.getClazz().equals(con.getTarget())) {
			getFieldSichten().add(new ConnectionSicht("to" + con.getKind(), name, con));
		}
	}

	public void addConnection(ObjectModell modell) throws Exception {
		for (Field f : modell.getClass().getFields()) {
			Object o = f.get(modell);
			if (o instanceof Connection) {
				this.addConnection(f.getName(), (Connection) o);
			}
		}
		modell.getFieldSichten().stream().forEach(o -> this.getFieldSichten().add(o));
		modell.getMethodSichten().stream().forEach(o -> this.getMethodSichten().add(o));

	}

	public Iterator<FieldOrMethod> getFieldValuesOfKind(String kind) {
		return getFieldSichten().stream().filter(x -> kind.equals(x.getKind())).iterator();
	}

}
