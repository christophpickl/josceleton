package net.sf.josceleton.connection.impl.service.motion;

import net.sf.josceleton.connection.api.Connection;
import net.sf.josceleton.connection.api.service.motion.MotionStream;

/**
 * @since 0.4
 */
public interface MotionStreamInternalFactory {

	/**
	 * @since 0.4
	 */
	MotionStream create(Connection connection);
	
}
