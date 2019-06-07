package org.nill.vorlagen.lists;

import java.util.List;

@FunctionalInterface
public interface ListTransform<Q,Z> {
	List<Z> transform(Q x);
}
