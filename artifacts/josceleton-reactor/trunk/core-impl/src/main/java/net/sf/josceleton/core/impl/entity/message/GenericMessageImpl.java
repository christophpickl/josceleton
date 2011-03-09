package net.sf.josceleton.core.impl.entity.message;

import net.sf.josceleton.core.api.entity.User;
import net.sf.josceleton.core.api.entity.message.GenericMessage;

abstract class GenericMessageImpl implements GenericMessage {
	
	private final User user;
	
	GenericMessageImpl(final User user) {
		this.user = user;
	}

	/** Default implementation for {@link JointMessage} and {@link UserMessage}. */
	public final User getUser() {
		return this.user;
	}
	
	// MINOR @DRY shouldnt be skeleton implementations of equals/hash/tostring in here? (to reuse it)
	
}
