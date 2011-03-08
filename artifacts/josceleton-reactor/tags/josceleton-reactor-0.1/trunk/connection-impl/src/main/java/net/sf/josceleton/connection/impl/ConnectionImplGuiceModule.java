package net.sf.josceleton.connection.impl;

import net.sf.josceleton.connection.api.Connector;

import com.google.inject.AbstractModule;
import com.google.inject.Scopes;
import com.google.inject.assistedinject.FactoryProvider;

public class ConnectionImplGuiceModule extends AbstractModule {

	@Override protected final void configure() {
		
		bind(Connector.class).to(ConnectorImpl.class).in(Scopes.SINGLETON);
		bind(OscMessageRouter.class).to(OscMessageRouterImpl.class).in(Scopes.SINGLETON);
		
		bind(ConnectionInternalFactory.class).toProvider(
				FactoryProvider.newFactory(ConnectionInternalFactory.class, ConnectionInternalImpl.class));
		
	}

}
