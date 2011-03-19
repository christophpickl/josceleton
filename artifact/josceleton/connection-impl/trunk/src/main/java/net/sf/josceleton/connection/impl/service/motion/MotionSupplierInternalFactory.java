package net.sf.josceleton.connection.impl.service.motion;

import net.sf.josceleton.connection.api.Connection;
import net.sf.josceleton.connection.api.service.motion.MotionSupplier;

/**
 * @since 0.4
 */
public interface MotionSupplierInternalFactory {

	/**
	 * @since 0.4
	 */
	MotionSupplier create(Connection connection);
	
}
