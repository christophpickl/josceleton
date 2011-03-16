package net.sf.josceleton.motion.impl.gesture.hitwall;

import java.util.Collection;

import net.sf.josceleton.core.api.entity.XyzDirection;
import net.sf.josceleton.core.api.entity.joint.Joint;
import net.sf.josceleton.motion.api.gesture.hitwall.HitWallConfig;
import net.sf.josceleton.motion.impl.gesture.AbstractJointableGestureConfig;

/**
 * @since 0.4
 */
class HitWallConfigImpl extends AbstractJointableGestureConfig implements HitWallConfig {
	
	private final float coordinate;
	
	private final XyzDirection direction;
	
	private final boolean triggerOnLower;
	
	
	public HitWallConfigImpl(final float coordinate, final XyzDirection direction,
			final boolean triggerLower, final Collection<Joint> relevantJoints) {
		super(relevantJoints);
		
		this.coordinate = coordinate;
		this.direction = direction;
		this.triggerOnLower = triggerLower;
	}
	
	
	@Override public final float getCoordinate() {
		return this.coordinate;
	}

	@Override public final XyzDirection getDirection() {
		return this.direction;
	}

	@Override public final boolean getTriggerOnLower() {
		return this.triggerOnLower;
	}

}
