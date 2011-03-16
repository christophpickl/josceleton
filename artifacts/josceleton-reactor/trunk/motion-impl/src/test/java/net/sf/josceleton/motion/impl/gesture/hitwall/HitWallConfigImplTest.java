package net.sf.josceleton.motion.impl.gesture.hitwall;

import java.util.Collection;

import net.sf.josceleton.core.api.entity.XyzDirection;
import net.sf.josceleton.core.api.entity.joint.Joint;
import net.sf.josceleton.motion.impl.gesture.AbstractJointableGestureConfig;
import net.sf.josceleton.motion.impl.gesture.AbstractJointableGestureConfigTest;

/**
 * @since 0.4
 */
public class HitWallConfigImplTest extends AbstractJointableGestureConfigTest {

	@Override protected final AbstractJointableGestureConfig newTestee(final Collection<Joint> relevantJoints) {
		return new HitWallConfigImpl(relevantJoints, 0.0F, XyzDirection.X, true);
	}

}
