package net.sf.josceleton.motion.impl.gesture.hitwall;

import java.util.Arrays;
import java.util.Collection;

import net.sf.josceleton.commons.exception.InvalidArgumentException;
import net.sf.josceleton.commons.test.EqualsDescriptor;
import net.sf.josceleton.core.api.entity.Direction;
import net.sf.josceleton.core.api.entity.joint.Joint;
import net.sf.josceleton.core.api.entity.joint.Joints;
import net.sf.josceleton.motion.api.gesture.hitwall.HitWallConfig;
import net.sf.josceleton.motion.impl.gesture.AbstractJointableGestureConfigTest;

import org.testng.annotations.Test;

/**
 * @since 0.4
 */
public class HitWallConfigImplTest extends AbstractJointableGestureConfigTest<HitWallConfig> {

	@Test(expectedExceptions = InvalidArgumentException.class,
			expectedExceptionsMessageRegExp = ".*1.5.*")
	public final void instantiatingWithWrongArgumentForX() {
		new HitWallConfigImpl(Arrays.asList((Joint) Joints.HEAD()), 1.5F, Direction.X, true);
	}

	@Test(expectedExceptions = InvalidArgumentException.class,
			expectedExceptionsMessageRegExp = ".*\\-0.1.*")
	public final void instantiatingWithWrongNegativeArgumentForX() {
		new HitWallConfigImpl(Arrays.asList((Joint) Joints.HEAD()), -0.1F, Direction.X, true);
	}

	@Test(expectedExceptions = InvalidArgumentException.class,
			expectedExceptionsMessageRegExp = ".*7.5.*")
	public final void instantiatingWithWrongArgumentForZ() {
		new HitWallConfigImpl(Arrays.asList((Joint) Joints.HEAD()), 7.5F, Direction.Z, true);
	}

	@Test
	public final void instantiatingWithRightArgumentForZ() {
		new HitWallConfigImpl(Arrays.asList((Joint) Joints.HEAD()), 6.5F, Direction.Z, true);
	}
	
	@Override protected final HitWallConfig newTestee(final Collection<Joint> relevantJoints) {
		return new HitWallConfigImpl(relevantJoints, 0.0F, Direction.X, true);
	}

	private HitWallConfig newTestee(final float coordinate, final Direction direction,
			final boolean triggerOnLower, final Joint... relevantJoints) {
		return new HitWallConfigImpl(Arrays.asList(relevantJoints), coordinate, direction, triggerOnLower);
	}

	@Override protected final EqualsDescriptor<HitWallConfig> createEqualsDescriptor() {
		return new EqualsDescriptor<HitWallConfig>(
				newTestee(0.0F, Direction.X, true, Joints.TORSO()),
				newTestee(0.0F, Direction.X, true, Joints.TORSO()),
				
				newTestee(0.5F, Direction.X, true, Joints.TORSO()),
				newTestee(0.0F, Direction.Y, true, Joints.TORSO()),
				newTestee(0.0F, Direction.X, false, Joints.TORSO()),
				newTestee(0.0F, Direction.X, true, Joints.HEAD()),
				
				newTestee(0.0F, Direction.X, true, Joints.NECK(), Joints.TORSO())
		);
	}

	@Override protected final Object createSameTesteeForEquals() {
		return newTestee(0.0F, Direction.X, true, Joints.NECK(), Joints.TORSO());
	}
	
}
