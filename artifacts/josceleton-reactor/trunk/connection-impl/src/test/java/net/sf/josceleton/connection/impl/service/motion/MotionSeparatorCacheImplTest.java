package net.sf.josceleton.connection.impl.service.motion;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import net.sf.josceleton.commons.test.jmock.AbstractMockeryTest;
import net.sf.josceleton.connection.api.Connection;
import net.sf.josceleton.connection.api.service.motion.MotionSeparator;
import net.sf.josceleton.connection.api.service.motion.MotionSeparatorCache;

import org.jmock.Expectations;
import org.testng.annotations.Test;

public class MotionSeparatorCacheImplTest extends AbstractMockeryTest {
	
	@Test public final void everything() {
		final Connection expectedConnection1 = this.mock(Connection.class, "connection1");
		final Connection expectedConnection2 = this.mock(Connection.class, "connection2");
		final MotionSeparator expectedSeparator1 = this.mock(MotionSeparator.class, "separator1");
		final MotionSeparator expectedSeparator2 = this.mock(MotionSeparator.class, "separator2");
		
		final MotionSeparatorFactory factory = this.mock(MotionSeparatorFactory.class);
		this.checking(new Expectations() { {
			oneOf(factory).create(expectedConnection1); will(returnValue(expectedSeparator1));
			oneOf(factory).create(expectedConnection2); will(returnValue(expectedSeparator2));
		}});
		final MotionSeparatorCache testee = new MotionSeparatorCacheImpl(factory);
		
		assertThat(testee.lookupMotionSeparator(expectedConnection1), is(expectedSeparator1));
		assertThat(testee.lookupMotionSeparator(expectedConnection1), is(expectedSeparator1));
		assertThat(testee.lookupMotionSeparator(expectedConnection2), is(expectedSeparator2));
		assertThat(testee.lookupMotionSeparator(expectedConnection1), is(expectedSeparator1));
		assertThat(testee.lookupMotionSeparator(expectedConnection2), is(expectedSeparator2));
	}
}
