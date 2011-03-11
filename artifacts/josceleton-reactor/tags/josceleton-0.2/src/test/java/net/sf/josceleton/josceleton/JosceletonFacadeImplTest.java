package net.sf.josceleton.josceleton;

import static org.hamcrest.MatcherAssert.assertThat;
import net.sf.josceleton.commons.test.jmock.AbstractMockeryTest;
import net.sf.josceleton.connection.api.Connection;
import net.sf.josceleton.connection.api.Connector;

import org.hamcrest.Matchers;
import org.jmock.Expectations;
import org.testng.annotations.Test;

import com.google.inject.Injector;

public class JosceletonFacadeImplTest extends AbstractMockeryTest {
	
	@Test public final void openConnectionSuccessfully() {
		final Connection expectedConnection = this.mock(Connection.class);
		final JosceletonFacade facade = this.createTestee(expectedConnection, null);
		final Connection actualConnection = facade.openConnection();
		
		assertThat(actualConnection, Matchers.is(expectedConnection));
	}
	
	@Test public final void openConnectionOnPortSuccessfully() {
		final Connection expectedConnection = this.mock(Connection.class);
		// MINOR @TEST check Connector.openConnectionOnPort(x) for illegal arguments (negative ports, ports > 65... max range!)
		final Integer expectedPort = Integer.valueOf(42);
		final JosceletonFacade facade = this.createTestee(expectedConnection, expectedPort );
		final Connection actualConnection = facade.openConnectionOnPort(expectedPort.intValue());
		
		assertThat(actualConnection, Matchers.is(expectedConnection));
	}
	
	private JosceletonFacade createTestee(final Connection expectedConnection, final Integer onPort) {
		final Connector mockedConnector = this.mock(Connector.class);
		
		this.checking(new Expectations() { {
			if(onPort != null) {
				oneOf(mockedConnector).openConnectionOnPort(onPort.intValue());
			} else {
				oneOf(mockedConnector).openConnection();
			}
			will(returnValue(expectedConnection));
		}});

		final Injector mockedInjector = this.mock(Injector.class);
		this.checking(new Expectations() { {
			oneOf(mockedInjector).getInstance(Connector.class);
			will(returnValue(mockedConnector));
		}});
		
		return new JosceletonFacadeImpl(mockedInjector);
	}
	
}
