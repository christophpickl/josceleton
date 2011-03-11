package net.sf.josceleton.connection.api.service;

import net.sf.josceleton.core.api.async.Listener;
import net.sf.josceleton.core.api.entity.User;

/**
 * @since 0.3
 */
public interface UserManagerListener extends Listener{

	void onUserWaiting(User user);
	
	void onUserProcessing(User user);
	
	/**
	 * ATTENTION: the passed <code>user</code> argument can be null.
	 * 
	 * TODO @SITE properly document why {@link UserManagerListener#onUserDead(User)} the argument can be null.
	 * 
	 * @param user can be null!
	 */
	void onUserDead(User user);
}
