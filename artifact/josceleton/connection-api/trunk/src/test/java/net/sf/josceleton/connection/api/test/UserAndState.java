package net.sf.josceleton.connection.api.test;

import net.sf.josceleton.core.api.entity.User;
import net.sf.josceleton.core.api.entity.UserState;

public class UserAndState {
	
	private final User user;
	
	private final UserState state;
	
	
	public UserAndState(final User user, final UserState state) {
		this.user = user;
		this.state = state;
	}
	
	public final User getUser() {
		return this.user;
	}
	
	public final UserState getState() {
		return this.state;
	}
}
