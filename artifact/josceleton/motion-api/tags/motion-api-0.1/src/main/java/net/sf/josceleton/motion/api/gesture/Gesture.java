package net.sf.josceleton.motion.api.gesture;

import net.sf.josceleton.connection.api.service.motion.MotionListener;
import net.sf.josceleton.core.api.async.Async;

/**
 * @since 0.4
 */
public interface Gesture<
		C extends GestureConfig,
		L extends GestureListener>
	extends MotionListener, // yes, we want to make Gesture a MotionListener in the public API
		Async<L> {
	
	// async communication only
	
}
