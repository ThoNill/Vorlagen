package org.nill.reactive;

import java.util.List;

@FunctionalInterface
public interface ListTransform<Q,Z> {
	List<Z> transform(Q x);
}
