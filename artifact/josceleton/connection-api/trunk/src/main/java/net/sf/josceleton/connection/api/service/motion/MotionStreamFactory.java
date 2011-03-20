package net.sf.josceleton.connection.api.service.motion;

import net.sf.josceleton.connection.api.Connection;

/**
 * @since 0.4
 */
public interface MotionStreamFactory {

	/**
	 * @since 0.4
	 */
	MotionStream create(Connection connection);
}
