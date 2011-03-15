package net.sf.josceleton.connection.impl.service.user;

import com.google.inject.AbstractModule;

/**
 * @since 0.3
 */
public class JosceletionConnectionImplServiceUserGuiceModule extends AbstractModule {

	@Override protected final void configure() {
		bind(UserServiceInternal.class).to(UserServiceImpl.class);
		bind(UserServiceCollection.class).to(UserServiceCollectionImpl.class);
	}

}
