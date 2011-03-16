package net.sf.josceleton.motion.impl.gesture;

import java.util.Collection;

import net.sf.josceleton.core.api.entity.Coordinate;
import net.sf.josceleton.core.api.entity.joint.Joint;
import net.sf.josceleton.core.api.entity.joint.Skeleton;
import net.sf.josceleton.core.impl.async.DefaultAsync;
import net.sf.josceleton.motion.api.gesture.Gesture;
import net.sf.josceleton.motion.api.gesture.GestureConfiguration;
import net.sf.josceleton.motion.api.gesture.GestureListener;

/**
 * @since 0.4
 */
public abstract class AbstractGesture<L extends GestureListener> extends DefaultAsync<L> implements Gesture<L> {
	
	private final Collection<Joint> interestingJoints;
	
	public AbstractGesture(final GestureConfiguration configuration) {
		this.interestingJoints = configuration.getJointsInterestedIn();
	}

	@Override public final void onMoved(
			final Joint movedJoint, final Coordinate updatedCoordinate, final Skeleton skeleton) {
		if(this.interestingJoints.contains(movedJoint) == true) {
			this.onMovedInteresting(movedJoint, updatedCoordinate, skeleton);
		}
	}
	
	protected final void dispatchGestureDetected(final Skeleton skeleton) {
		for (final L listener : this.getListeners()) {
			listener.onGestureDetected(skeleton);
		}
	}
	
	protected abstract void onMovedInteresting(Joint movedJoint, Coordinate updatedCoordinate, Skeleton skeleton);	
}
