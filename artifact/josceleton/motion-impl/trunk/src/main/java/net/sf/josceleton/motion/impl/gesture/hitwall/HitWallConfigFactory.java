package net.sf.josceleton.motion.impl.gesture.hitwall;

import java.util.Collection;

import net.sf.josceleton.core.api.entity.joint.Joint;
import net.sf.josceleton.core.api.entity.location.Direction;
import net.sf.josceleton.motion.api.gesture.hitwall.HitWallConfig;

public interface HitWallConfigFactory {
	
	HitWallConfig create(Collection<Joint> relevantJoints,
		float coordinate, Direction direction, boolean triggerOnLower);
	
}
