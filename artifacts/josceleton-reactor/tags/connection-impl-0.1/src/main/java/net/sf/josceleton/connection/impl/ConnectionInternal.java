package net.sf.josceleton.connection.impl;

import net.sf.josceleton.connection.api.Connection;

/**
 * Enhanced {@link Connection} class to initialize it by library and therefore user has to care about less things.
 */
public interface ConnectionInternal extends Connection {

	/**
	 * Kind of a init method; delegates establish() invocation to internal {@link OscPort} and starts rerouting.
	 * 
	 * @throws IllegalStateException if invoked more than one time.
	 * @see OscPort#establish()
	 * $see {@link OscMessageRouter#reroute(OscPort, OscMessageRouterCallback)}
	 */
	void establish();
	
}
