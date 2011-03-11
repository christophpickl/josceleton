package net.sf.josceleton.connection.impl.service;

import com.google.inject.AbstractModule;

/**
 * @since 0.3
 */
public class JosceletionConnectionImplServiceGuiceModule extends AbstractModule {

	@Override protected final void configure() {
		bind(UserServiceInternal.class).to(UserServiceImpl.class);
	}

}
