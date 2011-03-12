package net.sf.josceleton.connection.api.service;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

import java.util.List;

import org.hamcrest.Matchers;

import net.sf.josceleton.core.api.entity.User;
import net.sf.josceleton.core.api.entity.UserState;

@SuppressWarnings("boxing")
class UserServiceTestScenarioStep {
	
	private final String scenarioStateLabel;
	private final UserState actionCommand;
	private final int actionUserId;
	
	private final int[] expectedWaits;
	private final int[] expectedProcs;
	private final int[] expectedDeads;
	
	private final int[] expectedGetWaitUsers;
	private final int[] expectedGetProcUsers;
	
	public UserServiceTestScenarioStep(String scenarioStateLabel, UserState actionCommand, int actionUserId,
		int[] expectedWaits, int[] expectedProcs, int[] expectedDeads, int[] expectedGetWaitUsers, int[] expectedGetProcUsers) {
		this.scenarioStateLabel = scenarioStateLabel;
		this.actionCommand = actionCommand;
		this.actionUserId = actionUserId;
		this.expectedWaits = expectedWaits;
		this.expectedProcs = expectedProcs;
		this.expectedDeads = expectedDeads;
		this.expectedGetWaitUsers = expectedGetWaitUsers;
		this.expectedGetProcUsers = expectedGetProcUsers;
	}
	
	public final void assertExpects(List<User> waitingUsers, List<User> processingUsers, List<User> deadUsers) {
		this.assertUsersEquals("waiting", waitingUsers, this.expectedWaits);
		this.assertUsersEquals("processing", processingUsers, this.expectedProcs);
		this.assertUsersEquals("dead", deadUsers, this.expectedDeads);
	}
	
	private void assertUsersEquals(String userStateLabel, List<User> actualUsers, int[] expectedIds) {
		
		assertThat(userStateLabel + " users size missmatch (for " + this + ")", actualUsers.size(), equalTo(expectedIds.length));
		
		for (int i = 0; i < expectedIds.length; i++) {
			User currentActualUser = actualUsers.get(i);
			assertThat(userStateLabel + " user ID missmtach at index [" + i + "] for(" + this + ")",
				currentActualUser.getOsceletonId(), equalTo(expectedIds[i]));
		}
	}
	
	public final User executeActionWith(TestableUserServiceDispatcher service, final int expectedUserId) {
		final User actualUser;
		if(this.actionCommand != null) {
			actualUser = service.delegateLookupUserMessage(this.actionUserId, this.actionCommand);
		} else /* dispatch an arbitrary joint message*/ {
			actualUser = service.delegateLookupJointMessage(this.actionUserId);
		}
		return actualUser;
	}
	
	public int getActionUserId() {
		return this.actionUserId;
	}

	public final int[] getExpectedGetWaitUsers() {
		return this.expectedGetWaitUsers;
	}

	public final int[] getExpectedGetProcUsers() {
		return this.expectedGetProcUsers;
	}

	@Override public String toString() {
		return "Scenario '" + this.scenarioStateLabel + "'";
	}
	
}
