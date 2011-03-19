package net.sf.josceleton.commons.reflect;

import com.google.inject.AbstractModule;
import com.google.inject.Scopes;

/**
 * @since 0.1
 */
public class CommonsReflectModule extends AbstractModule {

	@Override protected final void configure() {
		bind(DynamicInstantiator.class).to(DynamicInstantiatorImpl.class).in(Scopes.SINGLETON);
	}

}
