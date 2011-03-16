package net.sf.josceleton.motion.api.gesture;

import net.sf.josceleton.core.api.entity.XyzDirection;

/**
 * @since 0.4
 */
public interface HitWallGestureBuilder extends GestureBuilder<GestureListener, HitWallGesture> {

	/**
	 * @since 0.4
	 */
	HitWallGestureBuilder direction(XyzDirection direction);

	/**
	 * @since 0.4
	 */
	HitWallGestureBuilder coordinateValue(float coordinateValue);

	/**
	 * @since 0.4
	 */
	HitWallGestureBuilder triggerLower(boolean triggerLower); // TODO rename triggerLower

}
