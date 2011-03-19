package net.sf.josceleton.core.impl.entity;

import net.sf.josceleton.core.api.entity.user.User;

/**
 * @since 0.3
 */
class UserFactoryImpl implements UserFactory {
	
	/** Containts the next available unique ID, starting by 1. */
	private int uniqueIdCounter = 1;
	
	// MINOR @TEST check internal unique ID generation

	/**
	 * @since 0.3
	 */
	@Override public final User create(final int osceletonId) {
		final User newUser = new UserImpl(this.uniqueIdCounter, osceletonId);
		
		this.uniqueIdCounter++;
		
		return newUser;
	}

}
