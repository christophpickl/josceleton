package net.sf.josceleton.motion.api.gesture;

import net.sf.josceleton.core.api.entity.XyzDirection;

/**
 * @since 0.4
 */
public interface HitWallGestureConfiguration extends GestureConfiguration {

	/**
	 * @since 0.4
	 */
	XyzDirection getDirection();

	/**
	 * @since 0.4
	 */
	float getCoordinateValue();

	/**
	 * @since 0.4
	 */
	boolean getTriggerLower();
	
}
