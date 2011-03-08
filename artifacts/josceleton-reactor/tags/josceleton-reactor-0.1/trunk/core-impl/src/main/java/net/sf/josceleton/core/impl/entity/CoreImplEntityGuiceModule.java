package net.sf.josceleton.core.impl.entity;

import com.google.inject.AbstractModule;
import com.google.inject.assistedinject.FactoryProvider;

public class CoreImplEntityGuiceModule extends AbstractModule {

	@Override protected final void configure() {
		
		bind(FactoryFacade.class).to(FactoryFacadeImpl.class);
		
		bind(CoordinateFactory.class).toProvider(
			FactoryProvider.newFactory(CoordinateFactory.class, CoordinateImpl.class));
		
		bind(UserFactory.class).toProvider(
				FactoryProvider.newFactory(UserFactory.class, UserImpl.class));
		
	}

}
