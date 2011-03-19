package net.sf.josceleton.connection.impl.service.motion;

import java.util.Collection;

import net.sf.josceleton.connection.api.service.motion.MotionSupplierFactory;
import net.sf.josceleton.connection.api.service.motion.MotionSupplierFactoryTest;

import org.jmock.Expectations;

public class MotionSupplierCacheImplTest extends MotionSupplierFactoryTest<MotionSupplierFactoryImpl> {

	@Override protected final MotionSupplierFactory createTestee(
			final Collection<ExpectedFactoryCreateInvocationsWithReturnValue> createInvocations) {
		
		final MotionSupplierInternalFactory factory = this.mock(MotionSupplierInternalFactory.class);
		
		this.checking(new Expectations() { {
			for (final ExpectedFactoryCreateInvocationsWithReturnValue invocation : createInvocations) {
				oneOf(factory).create(invocation.getConnection()); will(returnValue(invocation.getSupplier()));
			}
		}});
		
		return new MotionSupplierFactoryImpl(factory);
	}
	
}
