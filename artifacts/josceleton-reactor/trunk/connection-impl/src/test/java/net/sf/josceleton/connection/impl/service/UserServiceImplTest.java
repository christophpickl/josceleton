package net.sf.josceleton.connection.impl.service;

import static org.hamcrest.MatcherAssert.assertThat;

import java.util.LinkedList;
import java.util.List;

import net.sf.josceleton.commons.test.jmock.AbstractMockeryTest;
import net.sf.josceleton.connection.api.service.UserServiceListener;
import net.sf.josceleton.core.api.entity.User;
import net.sf.josceleton.core.api.entity.UserState;
import net.sf.josceleton.core.impl.entity.UserFactory;

import org.hamcrest.Matchers;
import org.jmock.Expectations;
import org.testng.annotations.Test;

public class UserServiceImplTest extends AbstractMockeryTest {
	
	@Test public final void lookupUserForUserMessageProperly() {
		final UserServiceListenerCollector collectingListener = new UserServiceListenerCollector();

		final Integer osceletonUserId = 42;

		final UserFactory factory = this.mock(UserFactory.class);
		final User mockedUser = this.mock(User.class);
		this.checking(new Expectations() {{
			oneOf(factory).create(with(any(int.class)), with(any(int.class)));// FIXME uniqueId
			will(returnValue(mockedUser));
		}});
		
		final UserServiceInternal service = new UserServiceImpl(factory);
		service.addListener(collectingListener);
		UserState userState = UserState.WAITING;
		
		final User actualUser = service.lookupUserForUserMessage(osceletonUserId , userState);
		
		assertThat(actualUser, Matchers.is(Matchers.sameInstance(mockedUser)));
//		assertThat(collectingListener.getDeadUsers(), FIXME check contents);
		
		// MINOR @TEST could use some testutil class: ConnectionListenerTestUtil (specific to emulate dispatcher for ConnectionListeners) // for .onJoint/UserMessage methods
	}
	
	static class UserServiceListenerCollector implements UserServiceListener {
		// MINOR @CODE DRY introduce "abstract/generic collecting listener" thingy (for each onXyz() => get arguments and store in list for each method)
		private List<User> deadUsers = new LinkedList<User>(); 
		private List<User> processingUsers = new LinkedList<User>(); 
		private List<User> waitingUsers = new LinkedList<User>(); 
		
		@Override public final void onUserDead(final User user) {
			this.deadUsers.add(user);
		}
		@Override public final void onUserProcessing(final User user) {
			this.processingUsers.add(user);
		}
		@Override public final void onUserWaiting(final User user) {
			this.waitingUsers.add(user);
		}
		
		public final List<User> getDeadUsers() {
			return this.deadUsers;
		}
		public final List<User> getProcessingUsers() {
			return this.processingUsers;
		}
		public final List<User> getWaitingUsers() {
			return this.waitingUsers;
		}
	}
}
