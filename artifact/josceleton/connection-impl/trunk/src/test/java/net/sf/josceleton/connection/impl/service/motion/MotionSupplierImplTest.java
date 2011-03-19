package net.sf.josceleton.connection.impl.service.motion;

import net.sf.josceleton.connection.api.Connection;
import net.sf.josceleton.connection.api.ConnectionListener;
import net.sf.josceleton.connection.api.service.motion.MotionSupplierTest;
import net.sf.josceleton.core.api.entity.message.JointMessage;
import net.sf.josceleton.core.api.entity.message.UserMessage;
import net.sf.josceleton.core.impl.entity.joint.SkeletonFactory;

public class MotionSupplierImplTest extends MotionSupplierTest<MotionSupplierImpl> {

	@Override protected final MotionSupplierImpl createTestee(
			final Connection connection, final SkeletonFactory skeletonFactory) {
		
		return new MotionSupplierImpl(connection, skeletonFactory);
	}

	@Override protected final ConnectionListener asConnectionListener(final MotionSupplierImpl testee) {
		return testee;
	}

	@Override
	protected final void dispatch(final JointMessage message, final MotionSupplierImpl testee) {
		testee.onJointMessage(message);
	}

	@Override
	protected final void dispatch(final UserMessage message, final MotionSupplierImpl testee) {
		testee.onUserMessage(message);
	}

}
