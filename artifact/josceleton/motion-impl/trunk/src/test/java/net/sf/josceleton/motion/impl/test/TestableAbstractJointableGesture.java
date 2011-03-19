package net.sf.josceleton.motion.impl.test;

import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

import net.sf.josceleton.core.api.entity.joint.Joint;
import net.sf.josceleton.core.api.entity.joint.Skeleton;
import net.sf.josceleton.core.api.entity.location.Coordinate;
import net.sf.josceleton.motion.api.gesture.GestureListener;
import net.sf.josceleton.motion.api.gesture.JointableGestureConfig;
import net.sf.josceleton.motion.impl.gesture.AbstractJointableGesture;

/**
 * @since 0.1
 */
public class TestableAbstractJointableGesture
	extends AbstractJointableGesture<JointableGestureConfig, GestureListener> {
	
	private final List<OnMovedRelevantJointParameter> parameters = new LinkedList<OnMovedRelevantJointParameter>();
	
	public TestableAbstractJointableGesture(final Collection<Joint> relevantJoints) {
		super(new JointableGestureConfig() {
			@Override public Collection<Joint> getRelevantJoints() {
				return relevantJoints;
			}
		});
	}

	@Override protected final void onMovedRelevantJoint(
			final Joint movedJoint,
			final Coordinate updatedCoordinate,
			final Skeleton skeleton) {
		this.parameters.add(new OnMovedRelevantJointParameter(movedJoint, updatedCoordinate, skeleton));
	}

	public final List<OnMovedRelevantJointParameter> getParameters() {
		return this.parameters;
	}
	
}
