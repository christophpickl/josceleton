package net.sf.josceleton.playground.motion.app2.framework.page;

import net.sf.josceleton.connection.api.service.motion.MotionStreamListener;
import net.sf.josceleton.core.api.entity.joint.Joint;
import net.sf.josceleton.core.api.entity.joint.Skeleton;
import net.sf.josceleton.core.api.entity.location.Coordinate;
import net.sf.josceleton.playground.motion.app2.framework.world.WorldChangedListener;
import net.sf.josceleton.playground.motion.app2.framework.world.WorldSnapshotFactory;

class ReroutingListener implements MotionStreamListener {

	private final WorldSnapshotFactory factory;
	
	private final WorldChangedListener listener;
	
	public ReroutingListener(WorldSnapshotFactory factory, WorldChangedListener listener) {
		this.factory = factory;
		this.listener = listener;
	}
	
	@Override
	public void onMoved(Joint movedJoint, Coordinate updatedCoordinate, Skeleton skeleton) {
		this.listener.onUpdated(this.factory.create(skeleton));
	}
	
}
