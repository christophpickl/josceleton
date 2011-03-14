package net.sf.josceleton.connection.api.test;

import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

import net.sf.josceleton.connection.api.service.UserServiceListener;
import net.sf.josceleton.core.api.entity.User;
import net.sf.josceleton.core.api.entity.UserState;

public class TestableUserServiceListener implements UserServiceListener {
	private final Collection<UserAndState> receivedMessages = new LinkedList<UserAndState>();
	
	@Override public final void onUserDead(final User user) {
		this.receivedMessages.add(new UserAndState(user, UserState.DEAD));
	}
	@Override public final void onUserProcessing(final User user) {
		this.receivedMessages.add(new UserAndState(user, UserState.PROCESSING));
	}
	@Override public final void onUserWaiting(final User user) {
		this.receivedMessages.add(new UserAndState(user, UserState.WAITING));
	}
	
	public final Collection<UserAndState> getReceivedMessages() {
		return this.receivedMessages;
	}
	// MINOR @TEST REFACTOR: instead of these, use getReceivedMessages() instead, as it provides messages in right order
	public final List<User> getWaitingUsers() {
		return this.getUsersWith(UserState.WAITING);
	}
	public final List<User> getProcessingUsers() {
		return this.getUsersWith(UserState.PROCESSING);
	}
	public final List<User> getDeadUsers() {
		return this.getUsersWith(UserState.DEAD);
	}
	private List<User> getUsersWith(final UserState state) {
		final List<User> result = new LinkedList<User>();
		for (UserAndState uas : this.receivedMessages) {
			if(uas.getState() == state) {
				result.add(uas.getUser());
			}
		}
		return result;
	}
}
