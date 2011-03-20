package net.sf.josceleton.core.impl.entity.location;

import net.sf.josceleton.core.api.entity.location.RangeScaler;

import com.google.inject.AbstractModule;
import com.google.inject.Scopes;
import com.google.inject.assistedinject.FactoryProvider;

public class CoreImplLocationModule extends AbstractModule {

	@Override protected final void configure() {

		bind(CoordinateFactory.class).toProvider(
			FactoryProvider.newFactory(CoordinateFactory.class, CoordinateImpl.class));
		
		bind(RangeScaler.class).to(RangeScalerImpl.class).in(Scopes.SINGLETON);
		
	}

}
