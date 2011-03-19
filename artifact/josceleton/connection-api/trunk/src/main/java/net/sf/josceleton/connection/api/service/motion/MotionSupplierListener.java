package net.sf.josceleton.connection.api.service.motion;

import net.sf.josceleton.core.api.async.Listener;
import net.sf.josceleton.core.api.entity.Coordinate;
import net.sf.josceleton.core.api.entity.joint.Joint;
import net.sf.josceleton.core.api.entity.joint.Skeleton;

/**
 * A per-user service to get notified about user movements, as well as stores most recent coordinates for all joints.
 * 
 * @since 0.4
 */
public interface MotionSupplierListener extends Listener {
	
	/**
	 * Will be invoked whenever the specific user has moved any joint/body part.
	 * 
	 * Most likely only the <code>skeleton</code> parameter will be used to do a full re-check of joint coordinates.
	 * 
	 * @param movedJoint which has been, obviously, moved
	 * @param updatedCoordinate the joint has moved to
	 * @param skeleton as it is most likely one wants to recheck all conditions when single joint moved
	 * @since 0.4
	 */
	void onMoved(Joint movedJoint, Coordinate updatedCoordinate, Skeleton skeleton);
	
}
