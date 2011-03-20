package net.sf.josceleton.connection.impl.service.motion;

import net.sf.josceleton.connection.api.Connection;
import net.sf.josceleton.connection.api.service.motion.ContinuousMotionSupplier;

public interface ContinuousMotionSupplierFactory {
	
	ContinuousMotionSupplier create(Connection connection);
	
}
