package net.sf.josceleton.connection.impl.service.motion;

import net.sf.josceleton.connection.api.Connection;
import net.sf.josceleton.connection.api.service.motion.MotionSeparator;

/**
 * @since 0.4
 */
public interface MotionSeparatorFactory {

	/**
	 * @since 0.4
	 */
	MotionSeparator create(Connection connection);
	
}
