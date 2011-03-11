package net.sf.josceleton.connection.impl;

import net.sf.josceleton.connection.impl.osc.OscPort;

public interface OscMessageRouter {
	
	void reroute(OscPort oscPort, OscMessageRouterCallback callback);
	
}
