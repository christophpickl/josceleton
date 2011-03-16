package net.sf.josceleton.motion.impl.gesture;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import net.sf.josceleton.core.api.entity.joint.Joint;
import net.sf.josceleton.motion.api.gesture.Gesture;
import net.sf.josceleton.motion.api.gesture.GestureBuilder;
import net.sf.josceleton.motion.api.gesture.GestureConfig;
import net.sf.josceleton.motion.api.gesture.GestureListener;

/**
 * @since 0.4
 */
public abstract class GestureBuilderImpl<
		B extends GestureBuilder<B, G, C, L>,
		G extends Gesture<C, L>,
		C extends GestureConfig,
		L extends GestureListener>
	implements GestureBuilder<B, G, C, L> {

	private Collection<Joint> pAttachedJoints;
	
	@Override public final B attachedJoints(final Joint atLeastOneJoint, final Joint... optionallyMoreJoints) {
		final Set<Joint> allJoints = new HashSet<Joint>(1 + optionallyMoreJoints.length);
		allJoints.add(atLeastOneJoint);
		allJoints.addAll(Arrays.asList(optionallyMoreJoints));
		
		this.pAttachedJoints = Collections.unmodifiableSet(allJoints);
		
//		return this;
		return (B) this; // FIXME generic type hack
	}
	
	protected final Collection<Joint> getPAttachedJoints() {
		return this.pAttachedJoints;
	}

}
