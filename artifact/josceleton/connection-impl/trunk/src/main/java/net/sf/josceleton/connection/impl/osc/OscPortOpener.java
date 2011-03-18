package net.sf.josceleton.connection.impl.osc;

public interface OscPortOpener {
	
	/**
	 * @throws OscPortOpeningException
	 */
	OscPort connect(int port);
	
}
