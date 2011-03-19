package net.sf.josceleton.connection.impl.service.motion;

import net.sf.josceleton.connection.api.Connection;
import net.sf.josceleton.connection.api.ConnectionListener;
import net.sf.josceleton.connection.api.service.motion.MotionSupplierTest;
import net.sf.josceleton.core.api.entity.message.JointMessage;
import net.sf.josceleton.core.api.entity.message.UserMessage;
import net.sf.josceleton.core.impl.entity.joint.SkeletonFactory;

public class MotionSeparatorImplTest extends MotionSupplierTest<MotionSeparatorImpl> {

	@Override protected final MotionSeparatorImpl createTestee(
			final Connection connection, final SkeletonFactory skeletonFactory) {
		
		return new MotionSeparatorImpl(connection, skeletonFactory);
	}

	@Override protected final ConnectionListener asConnectionListener(final MotionSeparatorImpl testee) {
		return testee;
	}

	@Override
	protected final void dispatch(final JointMessage message, final MotionSeparatorImpl testee) {
		testee.onJointMessage(message);
	}

	@Override
	protected final void dispatch(final UserMessage message, final MotionSeparatorImpl testee) {
		testee.onUserMessage(message);
	}

}
