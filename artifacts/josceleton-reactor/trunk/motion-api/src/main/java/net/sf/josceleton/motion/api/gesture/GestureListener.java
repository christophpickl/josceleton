package net.sf.josceleton.motion.api.gesture;

import net.sf.josceleton.core.api.async.Listener;
import net.sf.josceleton.core.api.entity.joint.Skeleton;

public interface GestureListener extends Listener {
	
	// TODO which arguments to use? skeleton, yes. gesture itself? maybe... interestingJoints, yep...
	void onGestureDetected(Skeleton skeleton);
	
}
