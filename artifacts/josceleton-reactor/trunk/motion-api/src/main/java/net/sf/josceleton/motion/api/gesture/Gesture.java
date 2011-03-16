package net.sf.josceleton.motion.api.gesture;

import net.sf.josceleton.connection.api.service.motion.MotionListener;
import net.sf.josceleton.core.api.async.Async;

/**
 * @since 0.4
 */
public interface Gesture<L extends GestureListener> extends MotionListener, Async<L> {
	
	// async communication only
	
}
