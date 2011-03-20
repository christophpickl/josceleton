package net.sf.josceleton.connection.impl.service.motion;

import java.util.Collection;

import net.sf.josceleton.connection.api.service.motion.MotionStreamFactory;
import net.sf.josceleton.connection.api.service.motion.MotionStreamFactoryTest;

import org.jmock.Expectations;

public class MotionStreamCacheImplTest extends MotionStreamFactoryTest<MotionStreamFactoryImpl> {

	@Override protected final MotionStreamFactory createTestee(
			final Collection<ExpectedFactoryCreateInvocationsWithReturnValue> createInvocations) {
		
		final MotionStreamInternalFactory factory = this.mock(MotionStreamInternalFactory.class);
		
		this.checking(new Expectations() { {
			for (final ExpectedFactoryCreateInvocationsWithReturnValue invocation : createInvocations) {
				oneOf(factory).create(invocation.getConnection()); will(returnValue(invocation.getStream()));
			}
		}});
		
		return new MotionStreamFactoryImpl(factory);
	}
	
}
