package net.sf.josceleton.connection.impl.service;

import net.sf.josceleton.connection.api.service.UserServiceSync;
import net.sf.josceleton.core.api.entity.User;

/**
 * @since 0.3
 */
public interface UserServiceCollection extends UserServiceSync {

	void add(User newUser);
	User get(Integer osceletonUserId);
	User remove(Integer osceletonUserId, UserServiceCollectionResponder responder);
	
	void moveToProcessing(User processingUser);
	
	void checkIfYetRegistered(final Integer osceletonUserId, UserServiceCollectionResponder responder);

	User getForJoint(Integer osceletonUserId, UserServiceCollectionResponder userServiceImpl);

}
