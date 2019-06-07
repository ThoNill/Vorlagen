package org.nill.lists;

import java.util.List;

@FunctionalInterface
public interface ListTransform<Q,Z> {
	List<Z> transform(Q x);
}
