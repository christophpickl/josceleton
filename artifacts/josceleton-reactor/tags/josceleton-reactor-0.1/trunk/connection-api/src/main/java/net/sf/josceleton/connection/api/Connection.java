package net.sf.josceleton.connection.api;

import net.sf.josceleton.core.api.async.Async;
import net.sf.josceleton.core.api.async.Closeable;

/**
/**
 * This type provides the most low-level acccess you can get from Josceleton.
 * 
 * Dispatches already transformed, high-level value objects in real-time without any modifications.
 * 
 * @since 0.1
 */
public interface Connection extends Async<ConnectionListener>, Closeable {

//	UserManager getUserManager();
	
//	MotionTracker getMotionTracker();
	
//	RecentMotionStorage getRecentMotionStorage();

}
