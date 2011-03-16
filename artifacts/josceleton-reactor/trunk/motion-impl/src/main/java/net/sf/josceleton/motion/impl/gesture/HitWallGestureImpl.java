package net.sf.josceleton.motion.impl.gesture;

import java.util.Collection;

import net.sf.josceleton.core.api.entity.Coordinate;
import net.sf.josceleton.core.api.entity.joint.Joint;
import net.sf.josceleton.core.api.entity.joint.Skeleton;
import net.sf.josceleton.motion.api.gesture.GestureListener;
import net.sf.josceleton.motion.api.gesture.HitWallGesture;

/**
 * @since 0.4
 */
class HitWallGestureImpl extends AbstractGesture<GestureListener> implements HitWallGesture {

	HitWallGestureImpl(Collection<Joint> interestingJoints) {
		super(interestingJoints);
	}

	@Override protected final void onMovedInteresting(Joint interestingJoint, Coordinate updatedCoordinate, Skeleton skeleton) {
		System.out.println("gesture moved: " + updatedCoordinate);
	}

}
