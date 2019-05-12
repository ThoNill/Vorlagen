package test;

import java.util.ArrayList;
import java.util.List;

import org.nill.lists.ListTransform;

public class TestVorlagenModellFabrik implements ListTransform<TestModell, TestModell> {

	public TestVorlagenModellFabrik() {
	}

	@Override
	public List<TestModell> transform(TestModell m) {
		List<TestModell> liste = new ArrayList<>();
		liste.add(m);
		return liste;
	};

}
