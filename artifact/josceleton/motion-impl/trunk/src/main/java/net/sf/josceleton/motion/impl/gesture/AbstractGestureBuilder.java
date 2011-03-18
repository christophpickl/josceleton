package net.sf.josceleton.motion.impl.gesture;

import net.sf.josceleton.motion.api.gesture.Gesture;
import net.sf.josceleton.motion.api.gesture.GestureBuilder;
import net.sf.josceleton.motion.api.gesture.GestureConfig;
import net.sf.josceleton.motion.api.gesture.GestureListener;

/**
 * @since 0.4
 */
public abstract class AbstractGestureBuilder<
		B extends GestureBuilder<B, G, C, L>,
		G extends Gesture<C, L>,
		C extends GestureConfig,
		L extends GestureListener>
	implements GestureBuilder<B, G, C, L> {
	
	// nothing to inherit from yet :)
	
}
