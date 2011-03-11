package net.sf.josceleton.connection.impl.service;

import net.sf.josceleton.core.api.entity.User;
import net.sf.josceleton.core.api.entity.UserState;

/**
 * Used by transformer.
 * 
 * @since 0.3
 */
public interface UserStore {

	User lookupUserForJointMessage(Integer osceletonUserId);

	User lookupUserForUserMessage(Integer osceletonUserId, UserState userState);

}
