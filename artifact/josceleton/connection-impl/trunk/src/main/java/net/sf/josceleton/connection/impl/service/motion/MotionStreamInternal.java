package net.sf.josceleton.connection.impl.service.motion;

import net.sf.josceleton.connection.api.ConnectionListener;
import net.sf.josceleton.connection.api.service.motion.MotionStream;

interface MotionStreamInternal
	extends MotionStream,
		ConnectionListener /* for internal API is also able to directly listen to a connection */ {
	
	// merge interface only
	
}
