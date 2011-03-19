package net.sf.josceleton.core.api.test;

import net.sf.josceleton.core.api.entity.User;
import net.sf.josceleton.core.api.entity.UserState;
import net.sf.josceleton.core.api.entity.message.UserMessage;

/**
 * @since 0.4
 */
public class TestableUserMessage implements UserMessage {
	
	private final User user;

	private final UserState state;
	
	
	public TestableUserMessage(final User user, final UserState state) {
		this.user = user;
		this.state = state;
	}

	@Override public final User getUser() {
		return this.user;
	}
	
	@Override public final UserState getUserState() {
		return this.state;
	}

}
