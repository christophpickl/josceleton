package net.sf.josceleton.connection.impl.service.user;

import net.sf.josceleton.connection.api.service.user.UsersCollection;
import net.sf.josceleton.core.api.entity.user.User;

/**
 * @since 0.3
 */
public interface UserServiceCollection extends UsersCollection {

	void add(User newUser);
	User get(Integer osceletonUserId);
	User remove(Integer osceletonUserId, UserServiceCollectionResponder responder);
	
	void moveToProcessing(User processingUser);
	
	void checkIfYetRegistered(final Integer osceletonUserId, UserServiceCollectionResponder responder);

	User getForJoint(Integer osceletonUserId, UserServiceCollectionResponder userServiceImpl);

}
