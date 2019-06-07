package test.object;

import org.nill.vorlagen.object.ObjectModell;
import org.nill.vorlagen.object.ddd.Service;

public class BeispielService extends ObjectModell implements Service{

	public String erg(String a, String b) {
		return a + b;
	};
}
