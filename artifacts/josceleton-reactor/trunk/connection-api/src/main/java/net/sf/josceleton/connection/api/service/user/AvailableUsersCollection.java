package net.sf.josceleton.connection.api.service.user;

import java.util.Collection;

import net.sf.josceleton.core.api.entity.User;

/**
 * @since 0.3
 */
public interface AvailableUsersCollection {

	/**
	 * @since 0.3
	 */
	Collection<User> getWaiting();

	/**
	 * @since 0.3
	 */
	Collection<User> getProcessing();
	
}
