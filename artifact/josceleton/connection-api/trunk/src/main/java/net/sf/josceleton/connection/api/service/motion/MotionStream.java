package net.sf.josceleton.connection.api.service.motion;

import net.sf.josceleton.core.api.async.AsyncFor;
import net.sf.josceleton.core.api.entity.user.User;

/**
 * @since 0.4
 */
public interface MotionStream extends AsyncFor<User, MotionStreamListener> {
	
	// async communication only
	
}
