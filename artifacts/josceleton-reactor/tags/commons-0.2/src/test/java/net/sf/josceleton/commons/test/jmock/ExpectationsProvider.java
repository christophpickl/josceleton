package net.sf.josceleton.commons.test.jmock;

import org.jmock.Expectations;

public interface ExpectationsProvider<E> {
	
	Expectations provide(final E entity);
	
}
