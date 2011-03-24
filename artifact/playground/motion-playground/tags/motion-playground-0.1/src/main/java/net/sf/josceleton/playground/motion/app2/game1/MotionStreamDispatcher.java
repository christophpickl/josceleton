package net.sf.josceleton.playground.motion.app2.game1;

import net.sf.josceleton.connection.api.service.motion.MotionStreamListener;
import net.sf.josceleton.core.api.entity.joint.Joint;
import net.sf.josceleton.core.api.entity.joint.Skeleton;
import net.sf.josceleton.core.api.entity.location.Coordinate;
import net.sf.josceleton.core.impl.async.DefaultAsync;

public class MotionStreamDispatcher extends DefaultAsync<MotionStreamListener> implements MotionStreamListener {
	
	public MotionStreamDispatcher(MotionStreamListener... listeners) {
		for (MotionStreamListener listener : listeners) {
			this.addListener(listener);
		}
	}
	
	@Override
	public void onMoved(Joint movedJoint, Coordinate updatedCoordinate, Skeleton skeleton) {
		for (MotionStreamListener listener : this.getListeners()) {
			listener.onMoved(movedJoint, updatedCoordinate, skeleton);
		}
	}
	
}