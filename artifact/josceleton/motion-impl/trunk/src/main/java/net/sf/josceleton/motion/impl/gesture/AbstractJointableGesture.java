package net.sf.josceleton.motion.impl.gesture;

import java.util.Collection;

import net.sf.josceleton.core.api.entity.Coordinate;
import net.sf.josceleton.core.api.entity.joint.Joint;
import net.sf.josceleton.core.api.entity.joint.Skeleton;
import net.sf.josceleton.motion.api.gesture.GestureListener;
import net.sf.josceleton.motion.api.gesture.JointableGesture;
import net.sf.josceleton.motion.api.gesture.JointableGestureConfig;

/**
 * @since 0.4
 */
public abstract class AbstractJointableGesture<C extends JointableGestureConfig, L extends GestureListener>
	extends AbstractGesture<C, L>
		implements JointableGesture<C, L> {

	private final Collection<Joint> relevantJoints;
	
	// TODO DESIGN refactor AbstractJointableGesture => favour composition over inheritance 
	public AbstractJointableGesture(final JointableGestureConfig configuration) {
		this.relevantJoints = configuration.getRelevantJoints();
	}

	/**
	 * Performance enhanced <code>onMoved</code> method, as it will only be called when a relevant joint was moved.
	 */
	protected abstract void onMovedRelevantJoint(Joint movedJoint, Coordinate updatedCoordinate, Skeleton skeleton);	
	
	/** {@inheritDoc} from {@link MotionSupplierListener} */
	@Override public final void onMoved(
			final Joint movedJoint, final Coordinate updatedCoordinate, final Skeleton skeleton) {
		
		if(this.relevantJoints.contains(movedJoint) == true) {
			this.onMovedRelevantJoint(movedJoint, updatedCoordinate, skeleton);
		}
	}
	
}
