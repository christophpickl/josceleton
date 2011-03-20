package net.sf.josceleton.connection.impl.service.motion;

import net.sf.josceleton.connection.api.Connection;
import net.sf.josceleton.connection.api.ConnectionListener;
import net.sf.josceleton.connection.api.service.motion.MotionStreamTest;
import net.sf.josceleton.core.api.entity.message.JointMessage;
import net.sf.josceleton.core.api.entity.message.UserMessage;
import net.sf.josceleton.core.impl.entity.joint.SkeletonFactory;

public class MotionStreamImplTest extends MotionStreamTest<MotionStreamImpl> {

	@Override protected final MotionStreamImpl createTestee(
			final Connection connection, final SkeletonFactory skeletonFactory) {
		return new MotionStreamImpl(connection, skeletonFactory);
	}

	@Override protected final ConnectionListener asConnectionListener(final MotionStreamImpl testee) {
		return testee;
	}

	@Override
	protected final void dispatch(final JointMessage message, final MotionStreamImpl testee) {
		testee.onJointMessage(message);
	}

	@Override
	protected final void dispatch(final UserMessage message, final MotionStreamImpl testee) {
		testee.onUserMessage(message);
	}

}
