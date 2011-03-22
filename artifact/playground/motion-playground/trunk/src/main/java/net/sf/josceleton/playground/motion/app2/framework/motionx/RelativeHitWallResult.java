package net.sf.josceleton.playground.motion.app2.framework.motionx;

import net.sf.josceleton.core.api.entity.location.Coordinate;

public class RelativeHitWallResult implements GestureResult {
	
	private final Coordinate coordinate;
	
	public RelativeHitWallResult(Coordinate coordinate) {
		this.coordinate = coordinate;
	}

	public Coordinate getCoordinate() {
		return this.coordinate;
	}

}
