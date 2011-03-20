package net.sf.josceleton.motion.api.gesture;

import net.sf.josceleton.connection.api.service.motion.MotionStreamListener;
import net.sf.josceleton.core.api.async.Async;

/**
 * @since 0.4
 */
public interface Gesture<
		C extends GestureConfig,
		L extends GestureListener>
	extends MotionStreamListener, // yes, we want to make Gesture a MotionStreamListener in the public API
		Async<L> {
	
	// async communication only
	
}
