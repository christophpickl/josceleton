package net.sf.josceleton.motion.api.gesture;

import net.sf.josceleton.core.api.entity.XyzDirection;

/**
 * @since 0.4
 */
public interface HitWallBuilder extends GestureBuilder<HitWallBuilder, HitWallGesture, HitWallConfig, HitWallListener> {

	/**
	 * @since 0.4
	 */
	HitWallBuilder direction(XyzDirection direction);

	/**
	 * @since 0.4
	 */
	HitWallBuilder coordinateValue(float coordinateValue);

	/**
	 * @since 0.4
	 */
	HitWallBuilder triggerLower(boolean triggerLower); // TODO rename triggerLower

}
