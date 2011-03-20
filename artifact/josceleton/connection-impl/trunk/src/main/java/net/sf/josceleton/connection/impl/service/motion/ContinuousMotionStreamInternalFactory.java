package net.sf.josceleton.connection.impl.service.motion;

import net.sf.josceleton.connection.api.service.motion.MotionStream;
import net.sf.josceleton.connection.api.service.user.UsersCollection;

interface ContinuousMotionStreamInternalFactory {
	
	ContinuousMotionStreamInternal create(MotionStream stream, UsersCollection users);
	
}
