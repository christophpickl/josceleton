package net.sf.josceleton.connection.impl.service;

import net.sf.josceleton.core.api.entity.User;

public interface UserServiceCollectionResponder {

	User lookupDeadUser(Integer osceletonUserId);
	
	User lookupProcessingUser(Integer osceletonUserId);
	
}
