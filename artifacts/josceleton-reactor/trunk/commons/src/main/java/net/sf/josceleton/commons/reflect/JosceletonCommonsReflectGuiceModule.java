package net.sf.josceleton.commons.reflect;

import com.google.inject.AbstractModule;
import com.google.inject.Scopes;
import com.google.inject.TypeLiteral;

/**
 * @since 0.1
 */
public class JosceletonCommonsReflectGuiceModule extends AbstractModule {

	@Override protected final void configure() {
		bind(DynamicInstantiator.class).to(DynamicInstantiatorImpl.class).in(Scopes.SINGLETON);
		
//		bind(TypeLiteral.get(ClassAdapter.class)).to(TypeLiteral.get(ClassAdapterImpl.class));
//		bind(ClassAdapterFactory.class).to(ClassAdapterFactoryImpl.class);
		// FIXME TypeLiteral bind(ClassAdapterFactory.class).to(ClassAdapterFactoryImpl.class);
	}

}
