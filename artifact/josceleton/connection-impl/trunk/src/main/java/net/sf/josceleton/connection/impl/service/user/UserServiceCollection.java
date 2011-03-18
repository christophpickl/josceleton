package net.sf.josceleton.connection.impl.service.user;

import net.sf.josceleton.connection.api.service.user.AvailableUsersCollection;
import net.sf.josceleton.core.api.entity.User;

/**
 * @since 0.3
 */
public interface UserServiceCollection extends AvailableUsersCollection {

	void add(User newUser);
	User get(Integer osceletonUserId);
	User remove(Integer osceletonUserId, UserServiceCollectionResponder responder);
	
	void moveToProcessing(User processingUser);
	
	void checkIfYetRegistered(final Integer osceletonUserId, UserServiceCollectionResponder responder);

	User getForJoint(Integer osceletonUserId, UserServiceCollectionResponder userServiceImpl);

}
