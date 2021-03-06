package net.sf.josceleton.connection.impl.service.user;

import net.sf.josceleton.connection.api.service.user.UserService;

/**
 * Extends the common <code>UserService</code> interface with the <code>UserStore</code> interface only.
 * 
 * Transformer needs another view of a single object, therefore merging two interfaces and implement it once.
 * 
 * @since 0.3
 * @see UserServiceImpl
 */
public interface UserServiceInternal extends
	UserService,
	UserStore,
	UserServiceCollectionResponder {
	
	// merge interface only
	
}
