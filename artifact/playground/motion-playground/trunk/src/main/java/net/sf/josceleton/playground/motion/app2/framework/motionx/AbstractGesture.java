package net.sf.josceleton.playground.motion.app2.framework.motionx;

import net.sf.josceleton.core.impl.async.DefaultAsync;

public abstract class AbstractGesture<R extends GestureResult> extends DefaultAsync<GestureListener<R>> implements Gesture<R> {
	
	protected final void dispatchGestureDetected(R result) {
		for(final GestureListener<R> listener : this.getListeners()) {
			listener.onGestureDetected(result);
		}
	}
	
}
