package net.sf.josceleton.connection.api.service.motion;

import net.sf.josceleton.connection.api.Connection;

/**
 * @since 0.4
 */
public interface MotionSupplierFactory {

	/**
	 * @since 0.4
	 */
	MotionSupplier create(Connection openedConnection);
}
