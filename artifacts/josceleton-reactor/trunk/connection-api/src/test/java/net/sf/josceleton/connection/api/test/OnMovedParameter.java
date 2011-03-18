package net.sf.josceleton.connection.api.test;

import net.sf.josceleton.core.api.entity.Coordinate;
import net.sf.josceleton.core.api.entity.joint.Joint;
import net.sf.josceleton.core.api.entity.joint.Skeleton;

/**
 * Value object containing method arguments for {@link MotionListener}.
 * 
 * @since 0.4
 */
public class OnMovedParameter {
	
	private final Joint movedJoint;
	
	private final Coordinate updatedCoordinate;
	
	private final Skeleton skeleton;
	
	
	public OnMovedParameter(
			final Joint movedJoint,
			final Coordinate updatedCoordinate,
			final Skeleton skeleton) {
		this.movedJoint = movedJoint;
		this.updatedCoordinate = updatedCoordinate;
		this.skeleton = skeleton;
	}


	public final Joint getMovedJoint() {
		return this.movedJoint;
	}

	public final Coordinate getUpdatedCoordinate() {
		return this.updatedCoordinate;
	}

	public final Skeleton getSkeleton() {
		return this.skeleton;
	}
	
}
