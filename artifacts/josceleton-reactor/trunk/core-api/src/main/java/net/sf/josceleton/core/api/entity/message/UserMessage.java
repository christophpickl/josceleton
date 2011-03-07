package net.sf.josceleton.core.api.entity.message;

import net.sf.josceleton.core.api.entity.UserState;

/**
 * @since 0.1
 */
public interface UserMessage extends GenericMessage {

	/**
	 * @since 0.1
	 */
	UserState getUserState();
	
}
