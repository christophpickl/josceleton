package net.sf.josceleton.josceleton;

import net.sf.josceleton.connection.api.Connection;
import net.sf.josceleton.connection.api.Connector;

import com.google.inject.Injector;


public class JosceletonFacadeImpl implements JosceletonFacade {
	
	private final Injector injector;
	
	private final Connector connector;
	
	
	public JosceletonFacadeImpl(final Injector injector) {
		this.injector = injector;
		this.connector = this.injector.getInstance(Connector.class);
	}
	
	@Override public final Connection openConnection() {
		return this.connector.openConnection();
	}

	@Override public final Connection openConnectionOnPort(final int port) {
		return this.connector.openConnectionOnPort(port);
	}

}
