package net.sf.josceleton.motion.api.gesture;

import net.sf.josceleton.core.api.entity.joint.Joint;

/**
 * @since 0.4
 */
public interface JointableGestureBuilder<
		B extends JointableGestureBuilder<B, G, C, L>,
		G extends JointableGesture<C, L>,
		C extends JointableGestureConfig,
		L extends GestureListener/* no custom JointableGestureListener needed */>
	extends GestureBuilder<B, G, C, L> {


	/**
	 * @since 0.4
	 */
	B relevantJoint(Joint atLeastOneJoint, Joint... optionallyMore);

	/**
	 * @since 0.4
	 */
	B relevantJoints(Joint[] joints);
	
	// LUXURY maybe offer as well a method for collections: relevantJoints(Collection<Joint> joints)
	
}
