package net.sf.josceleton.motion.impl.gesture;

import java.util.Arrays;

import net.sf.josceleton.core.api.entity.joint.Joint;
import net.sf.josceleton.core.api.entity.joint.Joints;
import net.sf.josceleton.core.api.entity.joint.Skeleton;
import net.sf.josceleton.core.api.test.TestableCoordinate;
import net.sf.josceleton.motion.api.gesture.GestureListener;
import net.sf.josceleton.motion.api.gesture.JointableGestureConfig;
import net.sf.josceleton.motion.impl.test.TestableAbstractJointableGesture;

import org.hamcrest.MatcherAssert;
import org.hamcrest.Matchers;
import org.testng.annotations.Test;

/**
 * @since 0.1
 */
@SuppressWarnings("boxing")
public class AbstractJointableGestureTest<
		C extends JointableGestureConfig,
		L extends GestureListener,
		G extends AbstractJointableGesture<C, L>>
	extends AbstractGestureTest<C, L, G> {
	
	
	@Test public final void onMovedCallsInternalMovedMethodOnlyForRelevantJoints() {
		final Joint relevantJoint = Joints.HEAD();
		final Joint irrelevantJoint = Joints.ANKLE().RIGHT();
		
		final TestableAbstractJointableGesture gesture = new TestableAbstractJointableGesture(Arrays.asList(relevantJoint));
		final Skeleton skeleton = this.mock(Skeleton.class);
		
		gesture.onMoved(irrelevantJoint, TestableCoordinate.newZeroed(), skeleton);
		MatcherAssert.assertThat(gesture.getParameters().size(), Matchers.equalTo(0));
		
		gesture.onMoved(relevantJoint, TestableCoordinate.newZeroed(), skeleton);
		MatcherAssert.assertThat(gesture.getParameters().size(), Matchers.equalTo(1));
		
	}
	
}
