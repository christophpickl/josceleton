package net.sf.josceleton.connection.api.service;

import net.sf.josceleton.connection.api.Connection;
import net.sf.josceleton.core.api.entity.User;

/**
 * 
 * @since 0.3
 */
public interface MotionServiceFactory {
	
	// this is a public factory to create motion services in is part of the API => has nothing to do with guice ;)
	
	MotionService create(Connection connection, User user);
	
}
