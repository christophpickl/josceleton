package net.sf.josceleton.motion.api.gesture;

/**
 * @since 0.4
 */
public interface JointableGesture<
		C extends JointableGestureConfig,
		L extends GestureListener>
	extends Gesture<C, L> {
	
	// marker interface only
	
}
