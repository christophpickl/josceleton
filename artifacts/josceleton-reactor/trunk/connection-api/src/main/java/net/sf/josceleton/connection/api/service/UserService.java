package net.sf.josceleton.connection.api.service;

import java.util.Collection;

import net.sf.josceleton.core.api.async.Async;
import net.sf.josceleton.core.api.entity.User;

/**
 * Provides access to available users and their state, specific to a single connection.
 * 
 * It offers two ways of getting user information:
 * <ul>
 *   <li><b>Getter</b>: TODO @DOC getCurrentUserCollection().getWaiting/getProcessingUsers():ImmutableIterable</li>
 *   <li><b>Async</b>: one callback method for each {@link UserState} (<code>WAITING</code>, <code>PROCESSING</code>, 
 *   					<code>DEAD</code>), all receiving an {@link User} argument associated with the message.</li>
 * </ul>
 * 
 * @since 0.3
 * @see Connection#getUserService()
 */
public interface UserService extends Async<UserServiceListener> {

	/**
	 * @since 0.3
	 */
	Collection<User> getWaitingUsers();

	/**
	 * @since 0.3
	 */
	Collection<User> getProcessingUsers();
	
}
