package net.sf.josceleton.motion.api.gesture;

import net.sf.josceleton.motion.api.gesture.hitwall.HitWallBuilder;

/**
 * @since 0.4
 */
public interface GestureFactoryFacade {

	/**
	 * @since 0.4
	 */
	HitWallBuilder newHitWall();
	
	// FUTURE: SwipeBuilder newSwipe();
	
	// ... etc ...
	
}
