package net.sf.josceleton.connection.api.service.motion;

import net.sf.josceleton.connection.api.Connection;

/**
 * @since 0.4
 */
public interface MotionServiceManager {

	/**
	 * @since 0.4
	 */
	MotionService lookupMotionService(Connection openedConnection);
}
