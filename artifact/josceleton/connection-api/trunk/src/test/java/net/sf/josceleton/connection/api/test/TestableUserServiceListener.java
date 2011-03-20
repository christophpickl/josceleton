package net.sf.josceleton.connection.api.test;

import java.util.LinkedList;
import java.util.List;

import net.sf.josceleton.connection.api.service.user.UserServiceListener;
import net.sf.josceleton.core.api.entity.user.User;
import net.sf.josceleton.core.api.entity.user.UserState;

public class TestableUserServiceListener implements UserServiceListener {
	
	// no custom OnUserXxxParameter type necessary, as it is only a single parameter
	private final List<User> onUserWaitingParameters = new LinkedList<User>();
	private final List<User> onUserProcessingParameters = new LinkedList<User>();
	private final List<User> onUserDeadParameters = new LinkedList<User>();
	
	private final List<UserAndState> onAnyParameter = new LinkedList<UserAndState>();
	
	@Override public final void onUserWaiting(final User user) {
		this.onUserWaitingParameters.add(user);
		this.onAnyParameter.add(new UserAndState(user, UserState.WAITING));
	}
	
	@Override public final void onUserProcessing(final User user) {
		this.onUserProcessingParameters.add(user);
		this.onAnyParameter.add(new UserAndState(user, UserState.PROCESSING));
	}
	
	@Override public final void onUserDead(final User user) {
		this.onUserDeadParameters.add(user);
		this.onAnyParameter.add(new UserAndState(user, UserState.DEAD));
	}
	
	public final List<User> getOnUserWaitingParameters() {
		return this.onUserWaitingParameters;
	}
	
	public final List<User> getOnUserProcessingParameters() {
		return this.onUserProcessingParameters;
	}
	
	public final List<User> getOnUserDeadParameters() {
		return this.onUserDeadParameters;
	}
	
	/**
	 * @return all parameters for any method invocation in right ORDER.
	 */
	public final List<UserAndState> getOnAnyParameter() {
		return this.onAnyParameter;
	}
	
}
