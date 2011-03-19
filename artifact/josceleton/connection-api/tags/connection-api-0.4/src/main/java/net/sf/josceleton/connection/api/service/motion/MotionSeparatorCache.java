package net.sf.josceleton.connection.api.service.motion;

import net.sf.josceleton.connection.api.Connection;

/**
 * @since 0.4
 */
public interface MotionSeparatorCache {

	/**
	 * @since 0.4
	 */
	MotionSeparator lookupMotionSeparator(Connection openedConnection);
}
