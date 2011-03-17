package net.sf.josceleton.motion.impl.gesture;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

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

	/** {@inheritDoc} from {@link JointableGestureBuilder} */
	@Override public final B relevantJoints(final Joint[] joints) {
		this.pAttachedJoints = CollectionUtil.toUnmodifiableSet(joints);
		return (B) this;
	}

	/** {@inheritDoc} from {@link JointableGestureBuilder} */
	@Override public final B relevantJoint(final Joint atLeastOneJoint, final Joint... optionallyMoreJoints) {
		this.pAttachedJoints = CollectionUtil.mergeToUnmodifiableSet(atLeastOneJoint, optionallyMoreJoints);
		
//		return this;
		return (B) this; // FIXME generic type hack
	}
	
	protected final Collection<Joint> getPAttachedJoints() {
		return this.pAttachedJoints;
	}
}
