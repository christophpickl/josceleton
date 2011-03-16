package net.sf.josceleton.motion.impl.gesture.hitwall;

import net.sf.josceleton.motion.api.gesture.HitWallGestureTest;
import net.sf.josceleton.motion.api.gesture.hitwall.HitWallConfig;
import net.sf.josceleton.motion.api.gesture.hitwall.HitWallGesture;
import net.sf.josceleton.motion.impl.gesture.hitwall.HitWallGestureImpl;

public class HitWallGestureImplTest extends HitWallGestureTest {

	@Override protected final HitWallGesture createTestee(final HitWallConfig configuration) {
		return new HitWallGestureImpl(configuration);
	}
	
}
