package net.sf.josceleton.motion.impl.test;

import net.sf.josceleton.core.api.entity.joint.Joint;
import net.sf.josceleton.core.api.entity.joint.Skeleton;
import net.sf.josceleton.core.api.entity.location.Coordinate;

/**
 * @since 0.1
 */
public class OnMovedRelevantJointParameter {

	private final Joint movedJoint;
	
	private final Coordinate updatedCoordinate;
	
	private final Skeleton skeleton;

	public OnMovedRelevantJointParameter(
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
