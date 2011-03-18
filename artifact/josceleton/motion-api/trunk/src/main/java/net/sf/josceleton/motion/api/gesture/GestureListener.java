package net.sf.josceleton.motion.api.gesture;

import net.sf.josceleton.core.api.async.Listener;
import net.sf.josceleton.core.api.entity.joint.Skeleton;

/**
 * @since 0.4
 */
public interface GestureListener
	extends Listener {

	/**
	 * @since 0.4
	 */
	void onGestureDetected(Skeleton skeleton);
	
}
