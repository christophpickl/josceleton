package net.sf.josceleton.connection.impl.service.motion;

import net.sf.josceleton.connection.api.Connection;
import net.sf.josceleton.connection.api.service.motion.ContinuousMotionStream;

public interface ContinuousMotionStreamFactory {
	
	ContinuousMotionStream create(Connection connection);
	
}
