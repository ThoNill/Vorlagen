package test;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

public class TestVorlagenModellFabrik implements Function<TestModell, List<TestModell>> {

	public TestVorlagenModellFabrik() {
	}

	@Override
	public List<TestModell> apply(TestModell m) {
		List<TestModell> liste = new ArrayList<>();
		liste.add(m);
		return liste;
	};

}
