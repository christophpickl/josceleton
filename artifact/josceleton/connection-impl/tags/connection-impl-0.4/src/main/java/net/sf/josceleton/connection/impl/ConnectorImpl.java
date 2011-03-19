package net.sf.josceleton.connection.impl;

import net.sf.josceleton.connection.api.Connection;
import net.sf.josceleton.connection.api.Connector;
import net.sf.josceleton.connection.impl.osc.OscPort;
import net.sf.josceleton.connection.impl.osc.OscPortOpener;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.google.inject.Inject;

public class ConnectorImpl implements Connector {
	
	private static final Log LOG = LogFactory.getLog(ConnectorImpl.class);
	
	private static final int DEFAULT_OSCELETON_PORT = 7110;
	
	private final OscPortOpener portOpener;

	
	private final ConnectionFactory connectionFactory;
	
	
	@Inject
	public ConnectorImpl(
			final OscPortOpener portOpener,
			final ConnectionFactory connectionFactory
			) {
		this.portOpener = portOpener;
		this.connectionFactory = connectionFactory;
	}

	/** {@inheritDoc} from {@link Connector} */
	@Override public final Connection openConnection() {
		LOG.debug("openConnection()");
		return this.openConnectionOnPort(DEFAULT_OSCELETON_PORT);
	}

	/** {@inheritDoc} from {@link Connector} */
	@Override public final Connection openConnectionOnPort(final int port) {
		LOG.debug("openConnectionOnPort(port=" + port + ")");
		
		final OscPort oscPort = this.portOpener.connect(port);
		
		final ConnectionInternal connection = this.connectionFactory.create(oscPort);
		connection.establish(); // ConnectionInternal enhances ordinary Connection with establish() method
		return connection;
	}
	
}
