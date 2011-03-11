package net.sf.josceleton.core.impl.entity;

import net.sf.josceleton.core.api.entity.User;

import com.google.inject.assistedinject.Assisted;

/**
 * Only used <code>UserService</code> to centrally create <code>User</code> instances.
 */
public interface UserFactory {
	
	User create(int osceletonId);
	
}
