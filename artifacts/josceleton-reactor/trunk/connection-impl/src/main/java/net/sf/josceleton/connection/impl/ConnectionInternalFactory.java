package net.sf.josceleton.connection.impl;

import net.sf.josceleton.connection.impl.osc.OscPort;


public interface ConnectionInternalFactory {
	
	ConnectionInternal create(OscPort oscPort);
	
}
