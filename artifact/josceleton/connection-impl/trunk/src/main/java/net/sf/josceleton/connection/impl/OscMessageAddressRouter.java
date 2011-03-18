package net.sf.josceleton.connection.impl;

import net.sf.josceleton.connection.impl.osc.OscPort;

public interface OscMessageAddressRouter {
	
	void reroute(OscPort oscPort, OscMessageAddressRouterCallback callback);
	
}
