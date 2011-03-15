package net.sf.josceleton.connection.api.service;

import net.sf.josceleton.core.api.async.Listener;
import net.sf.josceleton.core.api.entity.User;

/**
 * 
 * 
 * @since 0.3
 */
public interface UserServiceListener extends Listener {

	/**
	 * @since 0.3
	 */
	void onUserWaiting(User user);

	/**
	 * @since 0.3
	 */
	void onUserProcessing(User user);
	
	/**
	 * ATTENTION: the passed <code>user</code> argument can be null.
	 * 
	 * TODO @SITE properly document why {@link UserServiceListener#onUserDead(User)} the argument can be null.
	 * 
	 * @param user can be null!
	 * @since 0.3
	 */
	void onUserDead(User user);
}
