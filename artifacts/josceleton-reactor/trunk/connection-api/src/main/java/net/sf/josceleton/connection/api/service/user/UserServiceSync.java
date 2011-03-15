package net.sf.josceleton.connection.api.service.user;

import java.util.Collection;

import net.sf.josceleton.core.api.entity.User;

/**
 * @since 0.3
 */
public interface UserServiceSync {

	/**
	 * @since 0.3
	 */
	Collection<User> getWaitingUsers();

	/**
	 * @since 0.3
	 */
	Collection<User> getProcessingUsers();
	
}
