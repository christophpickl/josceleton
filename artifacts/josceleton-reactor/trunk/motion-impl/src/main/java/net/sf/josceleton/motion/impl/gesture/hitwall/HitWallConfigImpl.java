package net.sf.josceleton.motion.impl.gesture.hitwall;

import java.util.Collection;

import net.sf.josceleton.core.api.entity.XyzDirection;
import net.sf.josceleton.core.api.entity.joint.Joint;
import net.sf.josceleton.motion.api.gesture.hitwall.HitWallConfig;
import net.sf.josceleton.motion.impl.gesture.AbstractJointableGestureConfig;

import com.google.inject.Inject;
import com.google.inject.assistedinject.Assisted;

/**
 * @since 0.4
 */
class HitWallConfigImpl extends AbstractJointableGestureConfig implements HitWallConfig {
	
	private final float coordinate;
	
	private final XyzDirection direction;
	
	private final boolean triggerOnLower;
	
	
	@Inject HitWallConfigImpl(
			@Assisted final Collection<Joint> relevantJoints,
			@Assisted final float coordinate,
			@Assisted final XyzDirection direction,
			@Assisted final boolean triggerOnLower) {
		super(relevantJoints);
		
		this.coordinate = coordinate;
		this.direction = direction;
		this.triggerOnLower = triggerOnLower;
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
	
	@Override public final boolean equals(final Object other) {
		if(this == other) { return true; }
		if((other instanceof HitWallConfig) == false) { return false; }
		final HitWallConfig that = (HitWallConfig) other;
		return	this.getCoordinate() == that.getCoordinate() &&
				this.getDirection() == that.getDirection() &&
				this.getTriggerOnLower() == that.getTriggerOnLower() &&
				this.getRelevantJoints().equals(that.getRelevantJoints()); // MINOR DRY maybe move to superclass?
	}
	
	@Override public final int hashCode() {
		return this.direction.hashCode();
	}
	
	@Override public final String toString() {
		return "HitWallConfigImpl[coordinate=" + this.coordinate + ", direction=" + this.direction + ", " +
				"triggerOnLower=" + this.triggerOnLower + "]";
	}

}
