package net.sf.josceleton.connection.impl;

import net.sf.josceleton.connection.api.Connector;
import net.sf.josceleton.connection.api.ConnectorTest;
import net.sf.josceleton.connection.impl.osc.OscPort;
import net.sf.josceleton.connection.impl.osc.OscPortOpener;

import org.jmock.Expectations;

public class ConnectorImplTest extends ConnectorTest {
	
	@Override protected final Connector createTesteeWithoutPort() {
		return this.createConnector(null);
	}
	
	@Override protected final Connector createTesteeForPort(final int givenPort) {
		return this.createConnector(Integer.valueOf(givenPort));
	}
	
	private Connector createConnector(final Integer givenPort) {
		final int expectedPort = givenPort != null ? givenPort.intValue() : 7110;
		
		final OscPortOpener oscPortOpener = this.mock(OscPortOpener.class);
		final OscPort oscPort = this.mock(OscPort.class);
		final ConnectionInternalFactory connectionFactory = this.mock(ConnectionInternalFactory.class);
		final ConnectionInternal expectedConnection = this.mock(ConnectionInternal.class);
		
		this.checking(new Expectations() { {
			oneOf(oscPortOpener).connect(expectedPort);
			will(returnValue(oscPort));
			
			oneOf(connectionFactory).create(oscPort);
			will(returnValue(expectedConnection));
			
			oneOf(expectedConnection).establish();
		}});
		
		return new ConnectorImpl(oscPortOpener, connectionFactory);
	}
	
}
