package net.sf.josceleton.motion.api.gesture;

import net.sf.josceleton.core.api.entity.XyzDirection;

/**
 * @since 0.4
 */
public interface HitWallConfig extends GestureConfig {

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
