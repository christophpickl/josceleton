package net.sf.josceleton.motion.api.gesture;

import net.sf.josceleton.core.api.entity.joint.Joint;

/**
 * @since 0.4
 */
public interface GestureBuilder<
		B extends GestureBuilder<B, G, C, L>,
		G extends Gesture<C, L>,
		C extends GestureConfig,
		L extends GestureListener
> {

	/**
	 * @since 0.4
	 */
	B attachedJoints(Joint joint, Joint... moreJoints);

	/**
	 * @since 0.4
	 */
	G build();
	
	// LUXURY maybe it would be nice to be able to create a Gesture directly by its config and bypass Builder
//	G build(C configuration);
	
}
