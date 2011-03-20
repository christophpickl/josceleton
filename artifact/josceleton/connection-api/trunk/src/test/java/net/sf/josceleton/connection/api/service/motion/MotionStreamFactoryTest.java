package net.sf.josceleton.connection.api.service.motion;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

import java.util.Arrays;
import java.util.Collection;

import net.sf.josceleton.commons.test.jmock.AbstractMockeryTest;
import net.sf.josceleton.connection.api.Connection;

import org.testng.annotations.Test;

public abstract class MotionStreamFactoryTest<C extends MotionStreamFactory> extends AbstractMockeryTest {
	
	protected abstract MotionStreamFactory createTestee(
			final Collection<ExpectedFactoryCreateInvocationsWithReturnValue> createInvocations);

	@Test public final void everything() {
		final Connection expConn1 = this.mock(Connection.class, "expConn1");
		final Connection expConn2 = this.mock(Connection.class, "expConn2");
		final MotionStream expSupp1 = this.mock(MotionStream.class, "expSupp1");
		final MotionStream expSupp2 = this.mock(MotionStream.class, "expSupp2");
		
		final MotionStreamFactory testee = this.createTestee(Arrays.asList(
			new ExpectedFactoryCreateInvocationsWithReturnValue(expConn1, expSupp1),
			new ExpectedFactoryCreateInvocationsWithReturnValue(expConn2, expSupp2)
		));
		
		assertThat(testee.create(expConn1), is(expSupp1));
		assertThat(testee.create(expConn1), is(expSupp1));
		assertThat(testee.create(expConn2), is(expSupp2));
		assertThat(testee.create(expConn1), is(expSupp1));
		assertThat(testee.create(expConn2), is(expSupp2));
	}

	
	protected static class ExpectedFactoryCreateInvocationsWithReturnValue {
		
		private final Connection connection;
		
		private final MotionStream stream;
		
		public ExpectedFactoryCreateInvocationsWithReturnValue(final Connection connection,
				final MotionStream stream) {
			this.connection = connection;
			this.stream = stream;
		}
		
		public final Connection getConnection() {
			return this.connection;
		}
		
		public final MotionStream getStream() {
			return this.stream;
		}
	}
}
