package net.sf.josceleton.connection.api.service;


import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.notNullValue;

import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import net.sf.josceleton.commons.test.jmock.AbstractMockeryTest;
import net.sf.josceleton.connection.api.test.UserServiceListenerCollector;
import net.sf.josceleton.core.api.entity.User;
import net.sf.josceleton.core.api.entity.UserState;
import net.sf.josceleton.core.api.test.UserX;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hamcrest.Matchers;

@SuppressWarnings("boxing")
abstract class AbstractUserServiceTest extends AbstractMockeryTest {

	protected final UserServiceTestScenarioStep newStep(String scenarioStateLabel, UserState actionCommand, int actionUserId,
			int[] expectedWaits, int[] expectedProcs, int[] expectedDeads, int[] expectedGetWaitUsers, int[] expectedGetProcUsers) {
		return new UserServiceTestScenarioStep(scenarioStateLabel, actionCommand, actionUserId, expectedWaits, expectedProcs, expectedDeads, expectedGetWaitUsers, expectedGetProcUsers);
	}
	
	protected final int[] waitingUsers(final int... osceletonIds) { return osceletonIds; }
	
	protected final int[] processingUsers(final int... osceletonIds) { return osceletonIds; }
	
	protected final int[] deadUsers(final int... osceletonIds) { return osceletonIds; }
	
	
	protected abstract TestableUserServiceDispatcher createTestableTestee(User[] expectedCreatedUsers);
	
	protected final void assertScenarios(final UserServiceTestScenarioStep... steps) {
		final UserServiceListenerCollector collectingListener = new UserServiceListenerCollector();
		final User[] expectedCreatedUsers = newUsersByScenario(steps);
		final TestableUserServiceDispatcher service = this.createTestableTestee(expectedCreatedUsers);
		
		service.addListener(collectingListener);
		
		for (final UserServiceTestScenarioStep step : steps) {
			
			// the next line executes the functionality under test
			final User actualUser = step.executeActionWith(service, step.getActionUserId());
			
			assertThat("UserService may never return null values for its lookup methods!", actualUser, notNullValue());
			assertThat(actualUser.getId(), Matchers.is(Matchers.greaterThan(0))); // cant tell more about it :)
			assertThat(actualUser.getOsceletonId(), equalTo(step.getActionUserId()));
			
			// FIXME NO collectiong, but rather have to... extend scenario state??
			this.assertUsersEquals(step + "#waiting", service.getWaitingUsers(), step.getExpectedGetWaitUsers());
			this.assertUsersEquals(step + "#processing", service.getProcessingUsers(), step.getExpectedGetProcUsers());
			
			step.assertExpects(
				collectingListener.getWaitingUsers(),
				collectingListener.getProcessingUsers(),
				collectingListener.getDeadUsers()
			);
		}
	}
	
	private void assertUsersEquals(final String stepLabel, final Collection<User> serviceUsers, final int[] expectedUserIds) {
		assertThat("Users received by getters must be equal to " + stepLabel + " provided.\n" +
				"  1) getter: " + Arrays.toString(serviceUsers.toArray()) + "\n" +
				"  2) expect: " + Arrays.toString(expectedUserIds), serviceUsers.size(), equalTo(expectedUserIds.length));
		
		final Collection<User> usersLeft = new HashSet<User>(serviceUsers);
		
		for (int i = 0; i < expectedUserIds.length; i++) {
			final int expectedUserId = expectedUserIds[i];
			final User foundUser = this.findUserByOsceletonId(usersLeft, expectedUserId);
			assertThat(foundUser, notNullValue());
			usersLeft.remove(foundUser);
		}
		
		assertThat(usersLeft.isEmpty(), equalTo(true));
	}
	
	private User findUserByOsceletonId(final Collection<User> users, final int id) {
		for (User currentUser : users) {
			if(currentUser.getOsceletonId() == id) {
				return currentUser;
			}
		}
		return null;
	}
	
	private User[] newUsersByScenario(final UserServiceTestScenarioStep[] states) {
		// we expect UserService to create a User instance only once per new user (identified by osceletonId)
		final Set<Integer> yetUsedIds = new HashSet<Integer>();
		final List<User> newUsers = new LinkedList<User>();
		
		for (int i = 0; i < states.length; i++) {
			final UserServiceTestScenarioStep state = states[i];
			final int currentUserId = state.getActionUserId();
			
			if(yetUsedIds.contains(currentUserId) == false) { // skip duplicates
				yetUsedIds.add(currentUserId);
				newUsers.add(new UserX(currentUserId));
			}
		}
		
		return newUsers.toArray(new User[newUsers.size()]);
	}
	
}
