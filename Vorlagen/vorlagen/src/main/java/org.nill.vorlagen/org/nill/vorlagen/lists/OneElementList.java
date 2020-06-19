package org.nill.vorlagen.lists;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;
import java.util.function.UnaryOperator;

public class OneElementList<T> implements Function<T,List<T>> {
	private UnaryOperator<T> transform;

	public OneElementList() {
		this(UnaryOperator.identity());
	}
	public OneElementList(UnaryOperator<T> transform) {
		super();
		this.transform = transform;
	}

	@Override
	public List<T> apply(T x) {
		T tx = transform.apply(x);
		List<T> l = new ArrayList();
		l.add(tx);
		return l;
	}
	
}
