package net.sf.josceleton.motion.api.test;

import java.util.Arrays;
import java.util.Collection;

import net.sf.josceleton.core.api.entity.XyzDirection;
import net.sf.josceleton.core.api.entity.joint.Joint;
import net.sf.josceleton.motion.api.gesture.HitWallConfig;

public class TestableHitWallGestureConfiguration implements HitWallConfig {
	
	private final Collection<Joint> jointsInterestedIn;
	
	private final float coordinateValue;
	
	private final XyzDirection direction;
	
	private final boolean triggerLower;
	
	
	public TestableHitWallGestureConfiguration(
			final Collection<Joint> jointsInterestedIn,
			final float coordinateValue,
			final XyzDirection direction,
			final boolean triggerLower
			) {
		this.coordinateValue = coordinateValue;
		this.direction = direction;
		this.triggerLower = triggerLower;
		this.jointsInterestedIn = jointsInterestedIn;
	}

	@Override public final float getCoordinate() {
		return this.coordinateValue;
	}

	@Override public final XyzDirection getDirection() {
		return this.direction;
	}

	@Override public final boolean getTriggerOnLower() {
		return this.triggerLower;
	}

	@Override public final Collection<Joint> getRelevantJoints() {
		return this.jointsInterestedIn;
	}

	@Override public final String toString() {
		return "TestableHitWallGestureConfiguration[" +
				"coordinateValue=" + this.coordinateValue + ", " +
				"direction=" + this.direction + ", " +
				"triggerLower=" + this.triggerLower + ", " +
				"jointsInterestedIn=" + Arrays.toString(this.jointsInterestedIn.toArray()) +
				"]";
	}
	
}
