package net.sf.josceleton.connection.impl.service.user;

import net.sf.josceleton.core.api.entity.User;
import net.sf.josceleton.core.api.entity.UserState;

/**
 * Used by transformer.
 * 
 * @since 0.3
 */
public interface UserStore {

	User lookupUserForJointMessage(Integer osceletonUserId);
	
	/**
	 * @return can be null in only one special case: /lost_user received for unkown user (really rare case).
	 */
	User lookupUserForUserMessage(Integer osceletonUserId, UserState userState);

}
