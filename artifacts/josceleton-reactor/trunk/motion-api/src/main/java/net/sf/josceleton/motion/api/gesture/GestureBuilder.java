package net.sf.josceleton.motion.api.gesture;

import net.sf.josceleton.core.api.entity.joint.Joint;

/**
 * @since 0.4
 */
public interface GestureBuilder<L extends GestureListener, G extends Gesture<L>> {

	/**
	 * @since 0.4
	 */
	GestureBuilder<L, G> attachedJoints(Joint joint, Joint... moreJoints);

	/**
	 * @since 0.4
	 */
	G build();
	
//	LUXURY G build(C configuration);
	
}
