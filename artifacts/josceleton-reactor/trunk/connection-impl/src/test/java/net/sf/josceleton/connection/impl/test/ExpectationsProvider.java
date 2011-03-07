package net.sf.josceleton.connection.impl.test;

import org.jmock.Expectations;

public interface ExpectationsProvider<E> {
	
	Expectations provide(final E entity);
	
}
