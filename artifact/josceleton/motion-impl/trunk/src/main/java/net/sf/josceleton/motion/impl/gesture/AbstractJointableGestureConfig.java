package net.sf.josceleton.motion.impl.gesture;

import java.util.Collection;

import net.sf.josceleton.core.api.entity.joint.Joint;
import net.sf.josceleton.motion.api.gesture.JointableGestureConfig;

public abstract class AbstractJointableGestureConfig implements JointableGestureConfig {

	private final Collection<Joint> relevantJoints;
	
	
	public AbstractJointableGestureConfig(final Collection<Joint> relevantJoints) {
		this.relevantJoints = relevantJoints;
	}

	/** {@inheritDoc} from {@link JointableGestureConfig} */
	@Override public final Collection<Joint> getRelevantJoints() {
		return this.relevantJoints;
	}
	
}
