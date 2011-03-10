package net.sf.josceleton.josceleton;

import net.sf.josceleton.connection.api.Connection;
import net.sf.josceleton.connection.api.Connector;

import com.google.inject.Injector;

/**
 * @since 0.2
 */
public class JosceletonFacadeImpl implements JosceletonFacade {

	private final Injector injector;
	
	private final Connector connector;
	

	/**
	 * @since 0.2
	 */
	public JosceletonFacadeImpl(final Injector injector) {
		this.injector = injector;
		this.connector = this.injector.getInstance(Connector.class);
	}

	/**
	 * @since 0.2
	 */
	@Override public final Connection openConnection() {
		return this.connector.openConnection();
	}

	/**
	 * @since 0.2
	 */
	@Override public final Connection openConnectionOnPort(final int port) {
		return this.connector.openConnectionOnPort(port);
	}

}
