package net.sf.josceleton.motion.api.gesture.hitwall;

import net.sf.josceleton.core.api.entity.XyzDirection;
import net.sf.josceleton.motion.api.gesture.JointableGestureBuilder;

/**
 * @since 0.4
 */
public interface HitWallBuilder
	extends JointableGestureBuilder<
		HitWallBuilder,
		HitWallGesture,
		HitWallConfig,
		HitWallListener> {

	/**
	 * @since 0.4
	 */
	HitWallBuilder direction(XyzDirection direction);

	/**
	 * @since 0.4
	 */
	HitWallBuilder coordinate(float coordinate);

	/**
	 * @since 0.4
	 */
	HitWallBuilder triggerOnLower(boolean triggerOnLower);

}
