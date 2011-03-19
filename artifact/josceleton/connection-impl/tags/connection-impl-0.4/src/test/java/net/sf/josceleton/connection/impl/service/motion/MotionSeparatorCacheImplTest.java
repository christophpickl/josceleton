package net.sf.josceleton.connection.impl.service.motion;

import java.util.Collection;

import net.sf.josceleton.connection.api.service.motion.MotionSeparatorCache;
import net.sf.josceleton.connection.api.service.motion.MotionSeparatorCacheTest;

import org.jmock.Expectations;

public class MotionSeparatorCacheImplTest extends MotionSeparatorCacheTest<MotionSeparatorCacheImpl> {

	@Override protected final MotionSeparatorCache createTestee(
			final Collection<ExpectedFactoryCreateInvocationsWithReturnValue> createInvocations) {
		
		final MotionSeparatorFactory factory = this.mock(MotionSeparatorFactory.class);
		
		this.checking(new Expectations() { {
			for (final ExpectedFactoryCreateInvocationsWithReturnValue invocation : createInvocations) {
				oneOf(factory).create(invocation.getConnection()); will(returnValue(invocation.getSeparator()));
			}
		}});
		
		return new MotionSeparatorCacheImpl(factory);
	}
	
}
