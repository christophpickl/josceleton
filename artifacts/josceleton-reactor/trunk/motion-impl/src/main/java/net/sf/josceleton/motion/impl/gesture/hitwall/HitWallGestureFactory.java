package net.sf.josceleton.motion.impl.gesture.hitwall;

import net.sf.josceleton.motion.api.gesture.hitwall.HitWallConfig;
import net.sf.josceleton.motion.api.gesture.hitwall.HitWallGesture;

public interface HitWallGestureFactory {
	
	HitWallGesture create(HitWallConfig config);
	
}
