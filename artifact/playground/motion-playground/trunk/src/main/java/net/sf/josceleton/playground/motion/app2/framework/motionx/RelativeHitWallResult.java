package net.sf.josceleton.playground.motion.app2.framework.motionx;

import net.sf.josceleton.core.api.entity.joint.Joint;
import net.sf.josceleton.core.api.entity.location.Coordinate;

public class RelativeHitWallResult implements GestureResult {
	
	private final RelativeHitWallGesture source;
	
	private final Coordinate coordinate;
	
	private final Joint movedJoint;
	
	public RelativeHitWallResult(RelativeHitWallGesture source, Coordinate coordinate, Joint movedJoint) {
		this.source = source;
		this.coordinate = coordinate;
		this.movedJoint = movedJoint;
	}

	public RelativeHitWallGesture getSource() {
		return this.source;
	}

	public Coordinate getCoordinate() {
		return this.coordinate;
	}

	public Joint getMovedJoint() {
		return this.movedJoint;
	}
	
}
