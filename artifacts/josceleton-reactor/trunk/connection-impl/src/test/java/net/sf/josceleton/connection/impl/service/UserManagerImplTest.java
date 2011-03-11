package net.sf.josceleton.connection.impl.service;

import net.sf.josceleton.commons.test.jmock.AbstractMockeryTest;
import net.sf.josceleton.connection.api.service.UserManager;
import net.sf.josceleton.connection.api.service.UserManagerListener;
import net.sf.josceleton.core.api.entity.User;
import net.sf.josceleton.core.impl.entity.UserFactory;

import org.testng.annotations.Test;

public class UserManagerImplTest extends AbstractMockeryTest {
	
	@Test public final void FIXME() {
		final UserManagerListener dummListener = new UserManagerListener() {
			@Override public void onUserDead(User user) {
				
			}
			@Override public void onUserProcessing(User user) {
				
			}
			@Override public void onUserWaiting(User user) {
				
			}
		};
		
		// FIXME @UserManagerImplTest
		final UserFactory factory = this.mock(UserFactory.class);
		final UserManager manager = new UserManagerImpl(factory);
		manager.addListener(dummListener);
		
		// TODO @TEST could use some testutil class
		// ==> ConnectionListenerTestUtil (specific to emulating dispatcher for ConnectionListeners)
		
//		manager.onJointMessage(message)
//		manager.onUserMessage(message)
		
//		assertThat(dummListener.gotUserDead, equalTo(1));
	}
	
}
