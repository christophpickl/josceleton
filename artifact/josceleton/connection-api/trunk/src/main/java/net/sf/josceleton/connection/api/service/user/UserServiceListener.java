package net.sf.josceleton.connection.api.service.user;

import net.sf.josceleton.core.api.async.Listener;
import net.sf.josceleton.core.api.entity.user.User;

/**
 * @since 0.3
 */
public interface UserServiceListener extends Listener {

	/**
	 * Notification about a new tracked <code>User</code>, which needs to be calibrated via the Psi position yet.
	 * 
	 * @since 0.3
	 */
	void onUserWaiting(User user);

	/**
	 * Notification about a successfull calibration, soon joint messages will come ;)
	 * 
	 * @since 0.3
	 */
	void onUserProcessing(User user);
	
	/**
	 * Notification about a lost user.
	 * 
	 * ATTENTION: The passed <code>user</code> argument can be null!
	 * This can happen if we entered an already running session, and only received the last bit (lost message).
	 * To be honest, this is a very very rare case :) 
	 * 
	 * @param user which was lost, or null if was in unpredictable state.
	 * @since 0.3
	 */
	void onUserDead(User user);
}
