package net.sf.josceleton.connection.api.service.motion;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

import java.util.Arrays;
import java.util.Collection;

import net.sf.josceleton.commons.test.jmock.AbstractMockeryTest;
import net.sf.josceleton.connection.api.Connection;

import org.testng.annotations.Test;

public abstract class MotionSeparatorCacheTest<C extends MotionSeparatorCache> extends AbstractMockeryTest {
	
	protected abstract MotionSeparatorCache createTestee(
			final Collection<ExpectedFactoryCreateInvocationsWithReturnValue> createInvocations);

	@Test public final void everything() {
		final Connection expectedConnection1 = this.mock(Connection.class, "connection1");
		final Connection expectedConnection2 = this.mock(Connection.class, "connection2");
		final MotionSeparator expectedSeparator1 = this.mock(MotionSeparator.class, "separator1");
		final MotionSeparator expectedSeparator2 = this.mock(MotionSeparator.class, "separator2");
		
		final MotionSeparatorCache testee = this.createTestee(Arrays.asList(
			new ExpectedFactoryCreateInvocationsWithReturnValue(expectedConnection1, expectedSeparator1),
			new ExpectedFactoryCreateInvocationsWithReturnValue(expectedConnection2, expectedSeparator2)
		));
		
		assertThat(testee.lookupMotionSeparator(expectedConnection1), is(expectedSeparator1));
		assertThat(testee.lookupMotionSeparator(expectedConnection1), is(expectedSeparator1));
		assertThat(testee.lookupMotionSeparator(expectedConnection2), is(expectedSeparator2));
		assertThat(testee.lookupMotionSeparator(expectedConnection1), is(expectedSeparator1));
		assertThat(testee.lookupMotionSeparator(expectedConnection2), is(expectedSeparator2));
	}

	
	protected static class ExpectedFactoryCreateInvocationsWithReturnValue {
		
		private final Connection connection;
		
		private final MotionSeparator separator;
		
		public ExpectedFactoryCreateInvocationsWithReturnValue(final Connection connection,
				final MotionSeparator separator) {
			this.connection = connection;
			this.separator = separator;
		}
		
		public final Connection getConnection() {
			return this.connection;
		}
		
		public final MotionSeparator getSeparator() {
			return this.separator;
		}
	}
}
