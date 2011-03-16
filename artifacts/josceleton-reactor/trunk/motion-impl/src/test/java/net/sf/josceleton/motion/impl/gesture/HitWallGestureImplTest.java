package net.sf.josceleton.motion.impl.gesture;

import net.sf.josceleton.motion.api.gesture.HitWallGesture;
import net.sf.josceleton.motion.api.gesture.HitWallConfig;
import net.sf.josceleton.motion.api.gesture.HitWallGestureTest;

public class HitWallGestureImplTest extends HitWallGestureTest {

	@Override protected final HitWallGesture createTestee(final HitWallConfig configuration) {
		return new HitWallGestureImpl(configuration);
	}
	
}
