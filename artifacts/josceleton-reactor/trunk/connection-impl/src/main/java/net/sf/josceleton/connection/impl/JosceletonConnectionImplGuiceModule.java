package net.sf.josceleton.connection.impl;

import net.sf.josceleton.connection.api.Connector;

import com.google.inject.AbstractModule;
import com.google.inject.Scopes;
import com.google.inject.assistedinject.FactoryProvider;

public class JosceletonConnectionImplGuiceModule extends AbstractModule {

	@Override protected final void configure() {
		
		bind(Connector.class).to(ConnectorImpl.class).in(Scopes.SINGLETON);
		bind(OscMessageAddressRouter.class).to(OscMessageAddressRouterImpl.class).in(Scopes.SINGLETON);
		
		bind(ConnectionFactory.class).toProvider(
				FactoryProvider.newFactory(ConnectionFactory.class, ConnectionImpl.class));
		
	}

}
