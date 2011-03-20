package net.sf.josceleton.core.impl.entity;

import net.sf.josceleton.core.impl.entity.location.CoreImplLocationModule;
import net.sf.josceleton.core.impl.entity.user.CoreImplUserModule;

import com.google.inject.AbstractModule;
import com.google.inject.Scopes;

/**
 * 
 * @since 0.1
 */
public class CoreImplEntityModule extends AbstractModule {

	@Override protected final void configure() {
		
		install(new CoreImplLocationModule());
		install(new CoreImplUserModule());

		bind(FactoryFacade.class).to(FactoryFacadeImpl.class).in(Scopes.SINGLETON);
		
	}

}
