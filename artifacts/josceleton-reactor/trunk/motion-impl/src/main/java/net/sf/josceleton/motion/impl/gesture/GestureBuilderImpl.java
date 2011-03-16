package net.sf.josceleton.motion.impl.gesture;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import net.sf.josceleton.core.api.entity.joint.Joint;
import net.sf.josceleton.motion.api.gesture.Gesture;
import net.sf.josceleton.motion.api.gesture.GestureBuilder;
import net.sf.josceleton.motion.api.gesture.GestureListener;

/**
 * @since 0.4
 */
public abstract class GestureBuilderImpl<L extends GestureListener, G extends Gesture<L>>
	implements GestureBuilder<L, G> {

	private Collection<Joint> pAttachedJoints;
	
	@Override public final GestureBuilder<L, G> attachedJoints(final Joint joint, final Joint... moreJoints) {
		final Set<Joint> allJoints = new HashSet<Joint>(1 + moreJoints.length);
		allJoints.add(joint);
		allJoints.addAll(Arrays.asList(moreJoints));
		
		this.pAttachedJoints = Collections.unmodifiableSet(allJoints);
		return this;
	}
	
	protected final Collection<Joint> getPAttachedJoints() {
		return this.pAttachedJoints;
	}

}
