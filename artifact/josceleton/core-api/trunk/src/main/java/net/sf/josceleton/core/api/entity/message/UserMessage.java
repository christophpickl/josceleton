package net.sf.josceleton.core.api.entity.message;

import net.sf.josceleton.core.api.entity.user.User;
import net.sf.josceleton.core.api.entity.user.UserState;

/**
 * @since 0.1
 */
public interface UserMessage extends GenericMessage {

	/**
	 * @since 0.1
	 */
	User getUser();
	
	/**
	 * @since 0.1
	 */
	UserState getUserState();
	
}
