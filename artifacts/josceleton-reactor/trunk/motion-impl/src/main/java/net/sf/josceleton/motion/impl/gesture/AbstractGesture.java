package net.sf.josceleton.motion.impl.gesture;

import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;

import net.sf.josceleton.core.api.entity.Coordinate;
import net.sf.josceleton.core.api.entity.joint.Joint;
import net.sf.josceleton.core.api.entity.joint.Skeleton;
import net.sf.josceleton.core.impl.async.DefaultAsync;
import net.sf.josceleton.motion.api.gesture.Gesture;
import net.sf.josceleton.motion.api.gesture.GestureListener;

/**
 * @since 0.4
 */
public abstract class AbstractGesture<L extends GestureListener> extends DefaultAsync<L> implements Gesture<L> {
	
	private final Collection<Joint> interestingJoints;
	
	public AbstractGesture(final Collection<Joint> interestingJoints) {
		this.interestingJoints = Collections.unmodifiableCollection(new HashSet<Joint>(interestingJoints));
	}

	@Override public final void onMoved(
			final Joint movedJoint, final Coordinate updatedCoordinate, final Skeleton skeleton) {
		if(this.interestingJoints.contains(movedJoint) == true) {
			this.onMoved(movedJoint, updatedCoordinate, skeleton);
		}
	}
	
	protected final void dispatchGestureDetected() {
		for (final L listener : this.getListeners()) {
			listener.onGestureDetected();
		}
	}
	
	protected abstract void onMovedInteresting(Joint interestingJoint, Coordinate updatedCoordinate, Skeleton skeleton);	
}
