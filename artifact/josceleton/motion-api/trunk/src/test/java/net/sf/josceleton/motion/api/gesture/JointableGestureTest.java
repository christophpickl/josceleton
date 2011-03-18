package net.sf.josceleton.motion.api.gesture;

import net.sf.josceleton.commons.test.jmock.AbstractMockeryTest;

public abstract class JointableGestureTest<
		C extends JointableGestureConfig,
		L extends GestureListener,
		G extends JointableGesture<C, L>>
	extends AbstractMockeryTest {
	
//	protected abstract G createJointableGesture(Joint... joints);
//	@Test
//	MINOR @TEST public final void nothingHappensOnIrrelevantJointMove() {
//		final G jointableGesture = this.createJointableGesture(Joints.ANKLE().LEFT());
//		
//		jointableGesture.onMoved(Joints.ANKLE().LEFT(), TestableCoordinate.newZeroed(), this.mock(Skeleton.class));
//	}
	
}
