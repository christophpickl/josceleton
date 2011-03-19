package net.sf.josceleton.connection.api.service.motion;

import net.sf.josceleton.core.api.async.AsyncFor;
import net.sf.josceleton.core.api.entity.User;

/**
 * @since 0.4
 */
public interface MotionSeparator extends AsyncFor<User, MotionListener> {
	
	// async only
	
}
