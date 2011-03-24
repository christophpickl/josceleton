package net.sf.josceleton.playground.motion.app2.framework.motionx;

import net.sf.josceleton.connection.api.service.motion.MotionStreamListener;
import net.sf.josceleton.core.api.async.Async;

public interface Gesture<R extends GestureResult> extends Async<GestureListener<R>>, MotionStreamListener {
	
}
