package net.sf.josceleton.experimental;

import net.sf.josceleton.Josceleton;
import net.sf.josceleton.connection.api.Connection;
import net.sf.josceleton.connection.api.Connector;

/**
 * @since 0.5
 */
public class JosceletonConnector implements Connector {
	
	private static final Connector DELEGATE = Josceleton.getConnector();
	
	@Override public final Connection openConnection() {
		return DELEGATE.openConnection();
	}

	@Override public final Connection openConnectionOnPort(final int port) {
		return DELEGATE.openConnectionOnPort(port);
	}

}
