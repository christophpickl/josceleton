package net.sf.josceleton.connection.api;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.not;
import static org.hamcrest.Matchers.nullValue;
import net.sf.josceleton.commons.test.jmock.AbstractMockeryTest;

import org.testng.annotations.Test;


public abstract class ConnectorTest extends AbstractMockeryTest {

	protected abstract Connector createTesteeForPort(int port);
	protected abstract Connector createTesteeWithoutPort();
	
	@Test
	public final void testOpenConnection() {
		this.abstractTestOpenConnectionMaybeOnPort(null);
	}
	@Test
	public final void testOpenConnectionOnPort() {
		this.abstractTestOpenConnectionMaybeOnPort(Integer.valueOf(42));
	}
	
	private void abstractTestOpenConnectionMaybeOnPort(final Integer givenPort) {
		final Connector connector = givenPort != null ?
			this.createTesteeForPort(givenPort.intValue()) :
			this.createTesteeWithoutPort();
		
		// !!! let abstract create() also return expected(Raw)Connection to check if they are equal
		final Connection actualRawConnection;
		if(givenPort != null) {
			actualRawConnection = connector.openConnectionOnPort(givenPort.intValue());
		} else {
			actualRawConnection = connector.openConnection();
		}
		
		assertThat(actualRawConnection, not(nullValue()));
		
//		Matchers.typeCompatibleWith(baseType)
		
//		assertThat(actualRawConnection, is(ConnectionInternal.class));
//		final ConnectionInternal actualConnection = (ConnectionInternal) actualRawConnection;
//		assertThat(actualConnection, equalTo(expectedConnection));
		
		// afterwards tear down thing will happen
	}
}
