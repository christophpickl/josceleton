package net.sf.josceleton.connection.api.test;

import java.util.LinkedList;
import java.util.List;

import net.sf.josceleton.connection.api.service.motion.MotionStreamListener;
import net.sf.josceleton.core.api.entity.joint.Joint;
import net.sf.josceleton.core.api.entity.joint.Skeleton;
import net.sf.josceleton.core.api.entity.location.Coordinate;

public class TestableMotionStreamListener implements MotionStreamListener {
	
	private final List<OnMovedParameter> onMovedParameters = new LinkedList<OnMovedParameter>();
		
	@Override
	public final void onMoved(final Joint movedJoint, final Coordinate updatedCoordinate, final Skeleton skeleton) {
		this.onMovedParameters.add(new OnMovedParameter(movedJoint, updatedCoordinate, skeleton));
	}

	public final List<OnMovedParameter> getOnMovedParameters() {
		return this.onMovedParameters;
	}
	
}
