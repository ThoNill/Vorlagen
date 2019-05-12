package org.nill.object;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class ObjectModell extends HashMap<String, Named<WithKind>> {

	public ObjectModell() {
		super();
	}

	public ObjectModell(int initialCapacity, float loadFactor) {
		super(initialCapacity, loadFactor);
	}

	public ObjectModell(int initialCapacity) {
		super(initialCapacity);
	}

	public ObjectModell(Map<? extends String, ? extends Named<WithKind>> m) {
		super(m);
	}

	public void init() throws Exception {
		for (Field f : getClass().getDeclaredFields()) {
			Object v = f.get(this);
			if (v instanceof FieldValue || v instanceof Connection) {
				put(f.getName(), new Named(f.getName(), v));
			} else {
				put(f.getName(), new Named(f.getName(), new FieldValue("value", f.getType(), v)));
			}
		}
	}

	public void addConnection(Named<WithKind> FieldValue) {
		if (FieldValue.getType() instanceof Connection) {
			Connection con = (Connection) FieldValue.getType();
			if (this.getClass().equals(con.getSource())) {
				put(FieldValue.getName(), 
						new Named<WithKind>(FieldValue.getName(),
						new Connection("from" + con.getKind(), con.getSource(), con.getTarget())));
			}
			if (this.getClass().equals(con.getTarget())) {
				put(FieldValue.getName(),
						new Named<WithKind>(FieldValue.getName(),
								new Connection(
										"to" + con.getKind(),
										con.getSource(), con.getTarget())));

			}
		}
	}

	public void addConnection(ObjectModell modell) throws Exception {
		modell.init();
		modell.values().forEach(x -> this.addConnection(x));
	}

	private void initialization() {
		try {
			init();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public Iterator<Named<WithKind>> getFieldValuesOfKind(String kind) {
		return values().stream().filter(x -> kind.equals(x.getType().getKind())).iterator();
	}

}
