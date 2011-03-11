package net.sf.josceleton.connection.impl.service;

import net.sf.josceleton.connection.api.service.UserManager;

import com.google.inject.AbstractModule;

/**
 * @since 0.3
 */
public class JosceletionConnectionImplServiceGuiceModule extends AbstractModule {

	@Override protected final void configure() {
		bind(UserManager.class).to(UserManagerImpl.class);
	}

}
