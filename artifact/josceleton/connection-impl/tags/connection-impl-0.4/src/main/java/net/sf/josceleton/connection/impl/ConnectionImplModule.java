package net.sf.josceleton.connection.impl;

import net.sf.josceleton.connection.api.Connector;
import net.sf.josceleton.connection.impl.osc.ConnectionImplOscModule;
import net.sf.josceleton.connection.impl.service.motion.ConnectionImplServiceMotionModule;
import net.sf.josceleton.connection.impl.service.user.ConnectionImplServiceUserModule;

import com.google.inject.AbstractModule;
import com.google.inject.Scopes;
import com.google.inject.assistedinject.FactoryProvider;

/**
 * @since 0.4
 */
public class ConnectionImplModule extends AbstractModule {

	@Override protected final void configure() {
		bind(Connector.class).to(ConnectorImpl.class).in(Scopes.SINGLETON);
		bind(OscMessageAddressRouter.class).to(OscMessageAddressRouterImpl.class).in(Scopes.SINGLETON);
		bind(ConnectionFactory.class).toProvider(
				FactoryProvider.newFactory(ConnectionFactory.class, ConnectionImpl.class));
		
		this.install(new ConnectionImplOscModule());
		this.install(new ConnectionImplServiceMotionModule());
		this.install(new ConnectionImplServiceUserModule());
	}

}
