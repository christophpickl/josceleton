package net.sf.josceleton.core.impl.entity.user;

import net.sf.josceleton.core.api.entity.user.UserColorFactory;

import com.google.inject.AbstractModule;
import com.google.inject.Scopes;

public class CoreImplUserModule extends AbstractModule {

	@Override protected final void configure() {

		bind(UserFactory.class).to(UserFactoryImpl.class).in(Scopes.SINGLETON);
		bind(UserColorFactory.class).to(DefaultUserColorFactoryImpl.class).in(Scopes.SINGLETON);
		
	}

}
