package net.sf.josceleton.core.api.entity.location;

import com.google.inject.AbstractModule;

/**
 * @since 0.5
 */
public class CoreApiEntityLocationModule extends AbstractModule {

	@Override protected final void configure() {
		bind(RangeFactory.class).to(RangeFactoryImpl.class);
	}

}
