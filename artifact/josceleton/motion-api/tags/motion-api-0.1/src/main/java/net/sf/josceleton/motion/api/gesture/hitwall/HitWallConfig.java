package net.sf.josceleton.motion.api.gesture.hitwall;

import net.sf.josceleton.core.api.entity.XyzDirection;
import net.sf.josceleton.motion.api.gesture.JointableGestureConfig;

/**
 * @since 0.4
 */
public interface HitWallConfig
	extends JointableGestureConfig {

	/**
	 * @since 0.4
	 */
	XyzDirection getDirection();

	/**
	 * @since 0.4
	 */
	float getCoordinate();

	/**
	 * @since 0.4
	 */
	boolean getTriggerOnLower();
	
}
