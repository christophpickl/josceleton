package net.sf.josceleton.motion.impl.gesture.hitwall;

import java.util.Arrays;
import java.util.Collection;

import net.sf.josceleton.commons.test.EqualsDescriptor;
import net.sf.josceleton.core.api.entity.XyzDirection;
import net.sf.josceleton.core.api.entity.joint.Joint;
import net.sf.josceleton.core.api.entity.joint.Joints;
import net.sf.josceleton.motion.api.gesture.hitwall.HitWallConfig;
import net.sf.josceleton.motion.impl.gesture.AbstractJointableGestureConfigTest;

/**
 * @since 0.4
 */
public class HitWallConfigImplTest extends AbstractJointableGestureConfigTest<HitWallConfig> {

	@Override protected final HitWallConfig newTestee(final Collection<Joint> relevantJoints) {
		return new HitWallConfigImpl(relevantJoints, 0.0F, XyzDirection.X, true);
	}

	private HitWallConfig newTestee(final float coordinate, final XyzDirection direction,
			final boolean triggerOnLower, final Joint... relevantJoints) {
		return new HitWallConfigImpl(Arrays.asList(relevantJoints), coordinate, direction, triggerOnLower);
	}

	@Override protected final EqualsDescriptor<HitWallConfig> createEqualsDescriptor() {
		return new EqualsDescriptor<HitWallConfig>(
				newTestee(0.0F, XyzDirection.X, true, Joints.TORSO()),
				newTestee(0.0F, XyzDirection.X, true, Joints.TORSO()),
				
				newTestee(0.5F, XyzDirection.X, true, Joints.TORSO()),
				newTestee(0.0F, XyzDirection.Y, true, Joints.TORSO()),
				newTestee(0.0F, XyzDirection.X, false, Joints.TORSO()),
				newTestee(0.0F, XyzDirection.X, true, Joints.HEAD()),
				
				newTestee(0.0F, XyzDirection.X, true, Joints.NECK(), Joints.TORSO())
		);
	}

	@Override protected final Object createSameTesteeForEquals() {
		return newTestee(0.0F, XyzDirection.X, true, Joints.NECK(), Joints.TORSO());
	}
	
}
