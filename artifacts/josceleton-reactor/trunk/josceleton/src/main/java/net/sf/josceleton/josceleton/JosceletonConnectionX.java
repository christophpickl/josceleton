package net.sf.josceleton.josceleton;

import net.sf.josceleton.connection.api.Connection;
import net.sf.josceleton.connection.api.ConnectionListener;
import net.sf.josceleton.connection.api.service.UserService;

/**
 * @since 0.3
 */
public class JosceletonConnectionX implements ConnectionX {
	
	private Connection internalConnection;
	
	@Override public final void open(/* TODO optional port */) {
		this.internalConnection = Josceleton.openConnection();
	}
	
	@Override public final UserService getUserService() {
		return this.internalConnection.getUserService();
	}

	@Override public final void addListener(ConnectionListener listener) {
		this.internalConnection.addListener(listener);
	}

	@Override public final void removeListener(ConnectionListener listener) {
		this.internalConnection.removeListener(listener);
	}

	@Override public final void close() {
		this.internalConnection.close();
	}

}
