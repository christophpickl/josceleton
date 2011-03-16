package net.sf.josceleton.motion.impl.gesture;

import net.sf.josceleton.core.api.entity.joint.Skeleton;
import net.sf.josceleton.core.impl.async.DefaultAsync;
import net.sf.josceleton.motion.api.gesture.Gesture;
import net.sf.josceleton.motion.api.gesture.GestureConfig;
import net.sf.josceleton.motion.api.gesture.GestureListener;

/**
 * @since 0.4
 */
public abstract class AbstractGesture<C extends GestureConfig, L extends GestureListener>
	extends DefaultAsync<L>
	implements Gesture<C, L> {

	/**
	 * @since 0.4
	 */
	protected final void dispatchGestureDetected(final Skeleton skeleton) {
		for (final L listener : this.getListeners()) {
			listener.onGestureDetected(skeleton);
		}
	}
	
}
