package net.sf.josceleton.connection.api.service;

import net.sf.josceleton.core.api.entity.User;
import net.sf.josceleton.core.api.entity.UserState;

/**
 * Utility class for tests to have a cleaner API for emulating connection message dispatches.
 * 
 * @since 0.3
 */
public interface TestableUserServiceDispatcher extends UserService {
	
	User delegateLookupUserMessage(Integer osceletonUserId, UserState userState);
	
	User delegateLookupJointMessage(Integer osceletonUserId/* ignore bodypart and coordinates */);
	
}
