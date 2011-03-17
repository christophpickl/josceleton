package net.sf.josceleton.motion.api.gesture;

import net.sf.josceleton.core.api.async.Listener;
import net.sf.josceleton.core.api.entity.joint.Skeleton;

/**
 * @since 0.4
 */
public interface GestureListener extends Listener {
	
	// FIXME which argument should be passed to GestureListener?
	// *) skeleton, yes, definetely useful 
	// *) relevantJoints, yep... maybe create own type
	// *) gesture itself? maybe... maybe not, as introduces additional dependency (but would be handy)
	// *) old approach!!! was nice introducing <R ext GestureResult> type => each Gesture can have its specific result!
	// *) maybe also user? DEFINETELY NO!!! ... because when added gesture, one knew which user he passed!
	/**
	 * @since 0.4
	 */
	void onGestureDetected(Skeleton skeleton);
	
}
