package net.sf.josceleton.core.api;

import net.sf.josceleton.core.api.entity.CoreApiEntityModule;

import com.google.inject.AbstractModule;

/**
 * @since 0.5
 */
public class CoreApiModule extends AbstractModule {

	@Override protected final void configure() {
		install(new CoreApiEntityModule());
	}

}
