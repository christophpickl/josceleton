package net.sf.josceleton.connection.api.service.user;

import net.sf.josceleton.core.api.async.Async;

/**
 * Provides access to available users and their state, specific to a single connection.
 * 
 * It offers two ways of getting user information:
 * <ul>
 *   <li><b>Getter</b>: MINOR @DOC getCurrentUserCollection().getWaiting/getProcessingUsers():ImmutableIterable</li>
 *   <li><b>Async</b>: one callback method for each {@link UserState} (<code>WAITING</code>, <code>PROCESSING</code>, 
 *   					<code>DEAD</code>), all receiving an {@link User} argument associated with the message.</li>
 * </ul>
 * 
 * @since 0.3
 * @see Connection#getUserService()
 */
public interface UserService extends Async<UserServiceListener>, AvailableUsersCollection {
	
	// merge interface only
	
}
