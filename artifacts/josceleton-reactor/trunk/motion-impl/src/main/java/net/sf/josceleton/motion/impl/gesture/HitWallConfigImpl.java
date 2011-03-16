package net.sf.josceleton.motion.impl.gesture;

import java.util.Collection;

import net.sf.josceleton.core.api.entity.XyzDirection;
import net.sf.josceleton.core.api.entity.joint.Joint;
import net.sf.josceleton.motion.api.gesture.HitWallConfig;

public class HitWallConfigImpl implements HitWallConfig {
	
	private final float coordinateValue;
	
	private final XyzDirection direction;
	
	private final boolean triggerLower;
	
	private final Collection<Joint> jointsInterestedIn;
	
	
	public HitWallConfigImpl(final float coordinateValue, final XyzDirection direction,
			final boolean triggerLower, final Collection<Joint> jointsInterestedIn) {
		this.coordinateValue = coordinateValue;
		this.direction = direction;
		this.triggerLower = triggerLower;
		this.jointsInterestedIn = jointsInterestedIn;
	}

	@Override public final float getCoordinateValue() {
		return this.coordinateValue;
	}

	@Override public final XyzDirection getDirection() {
		return this.direction;
	}

	@Override public final boolean getTriggerLower() {
		return this.triggerLower;
	}

	@Override public final Collection<Joint> getJointsInterestedIn() {
		return this.jointsInterestedIn;
	}

}
