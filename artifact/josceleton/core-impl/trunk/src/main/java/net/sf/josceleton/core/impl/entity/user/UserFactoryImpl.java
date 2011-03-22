package net.sf.josceleton.core.impl.entity.user;

import com.google.inject.Inject;

import net.sf.josceleton.core.api.entity.user.User;
import net.sf.josceleton.core.api.entity.user.UserColorFactory;

/**
 * @since 0.3
 */
class UserFactoryImpl implements UserFactory {
	
	/** Containts the next available unique ID, starting by 1. */
	private int uniqueIdCounter = 1;
	
	private final UserColorFactory userColorFactory;
	// MINOR @TEST check internal unique ID generation
	
	@Inject UserFactoryImpl(final UserColorFactory userColorFactory) {
		this.userColorFactory = userColorFactory;
	}
	
	/**
	 * @since 0.3
	 */
	@Override public final User create(final int osceletonId) {
		final User newUser = new UserImpl(this.uniqueIdCounter, osceletonId, this.userColorFactory.create());
		
		this.uniqueIdCounter++;
		
		return newUser;
	}

}
