package net.sf.josceleton.josceleton.test.integration;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.not;

import java.util.List;

import net.sf.josceleton.connection.api.service.motion.MotionSeparator;
import net.sf.josceleton.connection.api.service.motion.MotionListener;
import net.sf.josceleton.connection.api.test.TestableMotionSeparatorListener;
import net.sf.josceleton.connection.api.test.UserAndState;
import net.sf.josceleton.core.api.entity.User;
import net.sf.josceleton.core.api.entity.UserState;
import net.sf.josceleton.core.api.entity.body.Body;

import org.testng.annotations.Test;

@SuppressWarnings("boxing")
public class MotionSeparatorIntegrationTest extends AbstractIntegrationTest {
	
	@Test
	public final void motionSeparatorSeparatesJointMessagesForTwoDifferentUsers() {
		final int u1 = 1;
		final int u2 = 2;
		final TestableMotionSeparatorListener listener1 = new TestableMotionSeparatorListener();
		final TestableMotionSeparatorListener listener2 = new TestableMotionSeparatorListener();
		this.emulateTwoNewUsers(u1, u2, listener1, listener2);

		assertThat(listener1.getOnMovedInvocations().size(), equalTo(0));
		assertThat(listener2.getOnMovedInvocations().size(), equalTo(0));
		
		dispatchJointMessage(u1, Body.HEAD(), 0, 0, 0);
		assertThat(listener1.getOnMovedInvocations().size(), equalTo(1));
		assertThat(listener2.getOnMovedInvocations().size(), equalTo(0));

		dispatchJointMessage(u2, Body.HEAD(), 0, 0, 0);
		assertThat(listener1.getOnMovedInvocations().size(), equalTo(1));
		assertThat(listener2.getOnMovedInvocations().size(), equalTo(1));
	}
	
	private void emulateTwoNewUsers(final int u1, final int u2,
			final MotionListener listener1, final MotionListener listener2) {
		final MotionSeparator motionSeparator = this.getMotionSeparator();
		
		dispatchUserMessage(u1, UserState.WAITING);
		{
			assertThat(this.getReceivedUserServiceMessages().size(), equalTo(1));
		}
		final User user1 = this.getReceivedUserServiceMessages().get(0).getUser();
		
		dispatchUserMessage(u1, UserState.PROCESSING);
		dispatchUserMessage(u2, UserState.PROCESSING); // user2 took a shortcut ;)
		{
			final List<UserAndState> yetReceivedUserMessages = this.getReceivedUserServiceMessages();
			assertThat(yetReceivedUserMessages.size(), equalTo(4)); // wait + proc for u1 + u2
			assertThat(yetReceivedUserMessages.get(0).getUser(), equalTo(yetReceivedUserMessages.get(1).getUser())); // user for #1 and #2 same
			assertThat(yetReceivedUserMessages.get(2).getUser(), equalTo(yetReceivedUserMessages.get(3).getUser())); // user for #3 and #4 same
		}
		final User user2 = this.getReceivedUserServiceMessages().get(3).getUser();
		{
			assertThat(this.getRawReceivedJointMessages().size(), equalTo(0)); // sanity check
			assertThat(user1, not(equalTo(user2)));
		}
		
		dispatchJointMessage(u1, Body.HEAD(), 0, 0, 0);
		assertThat(this.getRawReceivedJointMessages().size(), equalTo(1));
		
		motionSeparator.addListenerFor(user1, listener1);
		motionSeparator.addListenerFor(user2, listener2);
	}
	
}
