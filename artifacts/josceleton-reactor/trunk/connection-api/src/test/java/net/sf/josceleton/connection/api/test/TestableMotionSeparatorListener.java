package net.sf.josceleton.connection.api.test;

import java.util.LinkedList;
import java.util.List;

import net.sf.josceleton.connection.api.service.motion.MotionListener;
import net.sf.josceleton.core.api.entity.Coordinate;
import net.sf.josceleton.core.api.entity.joint.Joint;
import net.sf.josceleton.core.api.entity.joint.Skeleton;

public class TestableMotionSeparatorListener implements MotionListener {
	
	private final List<OnMovedInvocation> onMovedInvocations = new LinkedList<OnMovedInvocation>();
		
	@Override
	public final void onMoved(final Joint movedJoint, final Coordinate updatedCoordinate, final Skeleton skeleton) {
		this.onMovedInvocations.add(new OnMovedInvocation(movedJoint, updatedCoordinate, skeleton));
	}

	public final List<OnMovedInvocation> getOnMovedInvocations() {
		return this.onMovedInvocations;
	}
	
}
