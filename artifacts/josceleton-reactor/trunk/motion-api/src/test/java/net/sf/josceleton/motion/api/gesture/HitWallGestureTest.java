package net.sf.josceleton.motion.api.gesture;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;

import net.sf.josceleton.commons.test.jmock.AbstractMockeryTest;
import net.sf.josceleton.core.api.entity.XyzDirection;
import net.sf.josceleton.core.api.entity.joint.Joint;
import net.sf.josceleton.core.api.entity.joint.Joints;
import net.sf.josceleton.core.api.entity.joint.Skeleton;
import net.sf.josceleton.core.api.test.TestableCoordinate;
import net.sf.josceleton.motion.api.test.TestableGestureListener;
import net.sf.josceleton.motion.api.test.TestableHitWallGestureConfiguration;

import org.testng.annotations.Test;

/**
 * @since 0.4
 */
@SuppressWarnings("boxing")
public abstract class HitWallGestureTest extends AbstractMockeryTest {
	
	protected abstract HitWallGesture createTestee(HitWallGestureConfiguration configuration);
	
	@Test public final void commonCase() {
		final Joint joint = Joints.HAND().RIGHT();
		final Collection<Joint> jointsInterestedIn = newJoints(joint);
		final float coordinateValue = 0.5F;
		final XyzDirection direction = XyzDirection.Y;
		final boolean triggerLower = true;
		
		final HitWallGestureConfiguration configuration = new TestableHitWallGestureConfiguration(jointsInterestedIn, coordinateValue, direction, triggerLower);
		final HitWallGesture gesture = this.createTestee(configuration);
		
		final Skeleton skeleton = this.mock(Skeleton.class);
		final TestableGestureListener listener = new TestableGestureListener();
		gesture.addListener(listener);

		
		gesture.onMoved(joint, TestableCoordinate.newWithDirection(direction, 0.6F), skeleton);
		assertThat(listener.getGesturesDetected().size(), equalTo(0));
		
		gesture.onMoved(joint, TestableCoordinate.newWithDirection(direction, 0.4F), skeleton);
		assertThat(listener.getGesturesDetected().size(), equalTo(1));
		
		gesture.onMoved(joint, TestableCoordinate.newWithDirection(direction, 0.3F), skeleton);
		assertThat(listener.getGesturesDetected().size(), equalTo(1));

		gesture.onMoved(joint, TestableCoordinate.newWithDirection(direction, 0.6F), skeleton);
		assertThat(listener.getGesturesDetected().size(), equalTo(1));

		gesture.onMoved(joint, TestableCoordinate.newWithDirection(direction, 0.4F), skeleton);
		assertThat(listener.getGesturesDetected().size(), equalTo(2));
	}
	
	// TODO outsource method
	public static Collection<Joint> newJoints(final Joint... joints) {
		final Collection<Joint> result = new HashSet<Joint>(joints.length);
		result.addAll(Arrays.asList(joints));
		return result;
	}
}
