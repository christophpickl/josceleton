package net.sf.josceleton.connection.api.service;


import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.notNullValue;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import org.hamcrest.MatcherAssert;
import org.hamcrest.Matchers;

import net.sf.josceleton.commons.test.jmock.AbstractMockeryTest;
import net.sf.josceleton.connection.api.test.UserServiceListenerCollector;
import net.sf.josceleton.core.api.entity.User;
import net.sf.josceleton.core.api.entity.UserState;
import net.sf.josceleton.core.api.test.UserX;

@SuppressWarnings("boxing")
abstract class AbstractUserServiceTest extends AbstractMockeryTest {

	protected final UserServiceScenarioState newState(String scenarioStateLabel, UserState actionCommand, int actionUserId,
			int[] expectedWaits, int[] expectedProcs, int[] expectedDeads) {
		return new UserServiceScenarioState(scenarioStateLabel, actionCommand, actionUserId, expectedWaits, expectedProcs, expectedDeads);
	}
	
	protected final int[] waitingUsers(final int... osceletonIds) { return osceletonIds; }
	
	protected final int[] processingUsers(final int... osceletonIds) { return osceletonIds; }
	
	protected final int[] deadUsers(final int... osceletonIds) { return osceletonIds; }
	
	
	protected abstract TestableUserServiceDispatcher createTestableTestee(User[] expectedCreatedUsers);
	
	protected final void assertScenarios(final UserServiceScenarioState... states) {
		final UserServiceListenerCollector collectingListener = new UserServiceListenerCollector();
		final User[] expectedCreatedUsers = newUsersByStates(states);
		final TestableUserServiceDispatcher service = this.createTestableTestee(expectedCreatedUsers);
		
		service.addListener(collectingListener);
		
		for (final UserServiceScenarioState state : states) {
			final User actualUser = state.executeActionWith(service, state.getActionUserId());
			assertThat("UserService may never return null values for its lookup methods!", actualUser, notNullValue());
			assertThat(actualUser.getId(), Matchers.is(Matchers.greaterThan(0))); // can tell more about it :)
			assertThat(actualUser.getOsceletonId(), equalTo(state.getActionUserId()));
			
			state.assertExpects(
				collectingListener.getWaitingUsers(),
				collectingListener.getProcessingUsers(),
				collectingListener.getDeadUsers()
			);
		}
	}
	
	
	private User[] newUsersByStates(final UserServiceScenarioState[] states) {
		// we expect UserService to create a User instance only once per new user (identified by osceletonId)
		final Set<Integer> yetUsedIds = new HashSet<Integer>();
		final List<User> newUsers = new LinkedList<User>();
		
		for (int i = 0; i < states.length; i++) {
			final UserServiceScenarioState state = states[i];
			final int currentUserId = state.getActionUserId();
			
			if(yetUsedIds.contains(currentUserId) == false) { // skip duplicates
				yetUsedIds.add(currentUserId);
				newUsers.add(new UserX(currentUserId));
			}
		}
		
		return newUsers.toArray(new User[newUsers.size()]);
	}
	
}
