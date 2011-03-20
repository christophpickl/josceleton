package net.sf.josceleton.core.impl.entity.user;

import net.sf.josceleton.core.api.entity.user.User;

/**
 * Only used <code>UserService</code> to centrally create <code>User</code> instances.
 */
public interface UserFactory {
	
	User create(int osceletonId);
	
}
