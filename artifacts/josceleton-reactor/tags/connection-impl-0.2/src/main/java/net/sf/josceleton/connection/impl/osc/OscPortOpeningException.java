package net.sf.josceleton.connection.impl.osc;

import net.sf.josceleton.commons.exception.JosceletonException;

public class OscPortOpeningException extends JosceletonException {
	
	private final int port;
	
	protected OscPortOpeningException(final int port, final String message, final Throwable cause) {
		super(message, cause);
		this.port = port;
	}

	private static final long serialVersionUID = 2204544045569273955L;
	
	public static OscPortOpeningException newByPort(final int port, final Throwable cause) {
		return new OscPortOpeningException(port, "Could not create OSCPortIn with port number [" + port + "]!", cause);
	}

	public final int getPort() {
		return this.port;
	}
	
}
