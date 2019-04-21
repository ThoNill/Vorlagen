package org.nill.lists;


import java.util.function.Function;

import org.reactivestreams.Publisher;

import reactor.core.publisher.Flux;

public class ListPublisher<T,V> implements Function<T,Publisher<V>> {
	private ListTransform<T, V> transform;
	
	public ListPublisher(ListTransform<T, V> transform) {
		super();
		this.transform = transform;
	}

	@Override
	public Publisher<V> apply(T t) {
		return Flux.fromIterable(transform.transform(t));
	}

}
