package net.sf.josceleton;

import static org.hamcrest.MatcherAssert.assertThat;
import net.sf.josceleton.commons.test.jmock.AbstractMockeryTest;
import net.sf.josceleton.connection.api.Connection;
import net.sf.josceleton.connection.api.Connector;
import net.sf.josceleton.connection.api.service.motion.MotionStreamFactory;
import net.sf.josceleton.core.api.entity.location.RangeFactory;
import net.sf.josceleton.motion.api.gesture.GestureFactoryFacade;

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
		final RangeFactory mockedRangeFactory = this.mock(RangeFactory.class);
		final MotionStreamFactory mockedMotionStreamFactory = this.mock(MotionStreamFactory.class);
		final GestureFactoryFacade mockedGestureFactoryFacade = this.mock(GestureFactoryFacade.class);
		
		final Injector mockedInjector = this.mock(Injector.class);
		this.checking(new Expectations() { {
			oneOf(mockedInjector).getInstance(Connector.class); will(returnValue(mockedConnector));
			oneOf(mockedInjector).getInstance(RangeFactory.class); will(returnValue(mockedRangeFactory));
			oneOf(mockedInjector).getInstance(MotionStreamFactory.class); will(returnValue(mockedMotionStreamFactory));
			oneOf(mockedInjector).getInstance(GestureFactoryFacade.class); will(returnValue(mockedGestureFactoryFacade));
		}});
		
		return new JosceletonFacadeImpl(mockedInjector);
	}
	
}
