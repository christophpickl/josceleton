package net.sf.josceleton.motion.impl.gesture;

import java.util.Collection;

import net.sf.josceleton.commons.util.CollectionUtil;
import net.sf.josceleton.core.api.entity.joint.Joint;
import net.sf.josceleton.motion.api.gesture.GestureListener;
import net.sf.josceleton.motion.api.gesture.JointableGesture;
import net.sf.josceleton.motion.api.gesture.JointableGestureBuilder;
import net.sf.josceleton.motion.api.gesture.JointableGestureConfig;

public abstract class AbstractJointableGestureBuilder<
		B extends JointableGestureBuilder<B, G, C, L>,
		G extends JointableGesture<C, L>,
		C extends JointableGestureConfig,
		L extends GestureListener>
	extends AbstractGestureBuilder<B, G, C, L>
		implements JointableGestureBuilder<B, G, C, L> {


	private Collection<Joint> pAttachedJoints;
	
	// MINOR only little Generics glitch in here :(
	//       would need AbstractJointableGestureBuilder (or lower) as B,
	//       but can only define JointableGestureBuilder to not assume too much...
	
	/** {@inheritDoc} from {@link JointableGestureBuilder} */
	@SuppressWarnings("unchecked")
	@Override public final B relevantJoints(final Joint[] joints) {
		this.pAttachedJoints = CollectionUtil.toUnmodifiableSet(joints);
		return (B) this;
	}

	/** {@inheritDoc} from {@link JointableGestureBuilder} */
	@SuppressWarnings("unchecked")
	@Override public final B relevantJoint(final Joint atLeastOneJoint, final Joint... optionallyMoreJoints) {
		this.pAttachedJoints = CollectionUtil.mergeToUnmodifiableSet(atLeastOneJoint, optionallyMoreJoints);
		return (B) this;
	}
	
	protected final Collection<Joint> getPAttachedJoints() {
		return this.pAttachedJoints;
	}
}
