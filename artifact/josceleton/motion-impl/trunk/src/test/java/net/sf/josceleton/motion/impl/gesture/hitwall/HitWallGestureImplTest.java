package net.sf.josceleton.motion.impl.gesture.hitwall;

import net.sf.josceleton.commons.util.CollectionUtil;
import net.sf.josceleton.core.api.entity.Direction;
import net.sf.josceleton.core.api.entity.joint.Joint;
import net.sf.josceleton.core.api.entity.joint.Joints;
import net.sf.josceleton.core.api.entity.joint.Skeleton;
import net.sf.josceleton.core.api.test.TestableCoordinate;
import net.sf.josceleton.motion.api.gesture.HitWallGestureTest;
import net.sf.josceleton.motion.api.gesture.hitwall.HitWallConfig;
import net.sf.josceleton.motion.api.gesture.hitwall.HitWallGesture;
import net.sf.josceleton.motion.api.gesture.hitwall.HitWallListener;
import net.sf.josceleton.motion.api.test.TestableHitWallGestureConfiguration;

import org.jmock.Expectations;
import org.testng.annotations.Test;

/**
 * @since 0.4
 */
public class HitWallGestureImplTest extends HitWallGestureTest {

	@Override protected final HitWallGesture createTestee(final HitWallConfig configuration) {
		return new HitWallGestureImpl(configuration);
	}
	
	// MINOR @TEST this actually belong to JointableGestureTest class!!!
	@Test public final void nothingHappensOnIrrelevantJointMove() {
		final Joint relevantJoint = Joints.ANKLE().LEFT();
		final Joint irrelevantJoint = Joints.ANKLE().RIGHT();
		
		final HitWallGesture gesture = this.createJointableGesture(relevantJoint);
		final HitWallListener listener = this.mock(HitWallListener.class);
		gesture.addListener(listener);
		final Skeleton skeleton = this.mock(Skeleton.class);
		this.checking(new Expectations() { {
			never(listener).onGestureDetected(skeleton);
		}});
		
		gesture.onMoved(irrelevantJoint, TestableCoordinate.newWithZ(0.3F), skeleton);
	}
	
	private HitWallGesture createJointableGesture(final Joint... joints) {
		return new HitWallGestureImpl(new TestableHitWallGestureConfiguration(CollectionUtil.toUnmodifiableSet(joints),
			0.5F, Direction.Z, true));
	}
	
}
