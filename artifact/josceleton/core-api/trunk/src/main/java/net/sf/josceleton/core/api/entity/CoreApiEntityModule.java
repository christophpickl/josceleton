package net.sf.josceleton.core.api.entity;

import net.sf.josceleton.core.api.entity.location.CoreApiEntityLocationModule;

import com.google.inject.AbstractModule;

/**
 * @since 0.5
 */
public class CoreApiEntityModule extends AbstractModule {

	@Override protected final void configure() {
		install(new CoreApiEntityLocationModule());
	}

}
