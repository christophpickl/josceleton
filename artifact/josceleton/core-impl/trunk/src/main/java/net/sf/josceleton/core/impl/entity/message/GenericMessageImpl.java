package net.sf.josceleton.core.impl.entity.message;

import net.sf.josceleton.core.api.entity.message.GenericMessage;
import net.sf.josceleton.core.api.entity.user.User;

abstract class GenericMessageImpl implements GenericMessage {
	
	private final User user;
	
	GenericMessageImpl(final User user) {
		this.user = user;
	}

	/** Default implementation for {@link JointMessage} and {@link UserMessage}. */
	public final User getUser() {
		return this.user;
	}
	
}
