package net.sf.josceleton.test.integration;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

import java.util.Arrays;
import java.util.Collection;

import net.sf.josceleton.connection.api.test.UserAndState;
import net.sf.josceleton.core.api.entity.joint.Joints;
import net.sf.josceleton.core.api.entity.user.UserState;

import org.testng.annotations.Test;

public class UserServiceFallbackIntegrationTest extends AbstractIntegrationTest<UserServiceFallbackIntegrationTest> {

	@Test public final void twoSameUsersMustNotFail() {
		dispatchUserMessage(1, UserState.WAITING);
		dispatchUserMessage(1, UserState.WAITING);
	}
	
	@SuppressWarnings("boxing")
	@Test public final void emulateLoopedRecording() {
		int expectedUserMsgs = 0;
		
		dispatchJointMessage(1, Joints.HEAD()); expectedUserMsgs += 2; // immediate waiting + processing
		dispatchJointMessage(2, Joints.HEAD()); expectedUserMsgs += 2; // immediate waiting + processing
		
		dispatchUserMessage(1, UserState.WAITING); expectedUserMsgs += 3; // two dead, one new wating
		assertThat("Actual joint messages: " + Arrays.toString(getReceivedJointMessages().toArray()), getReceivedJointMessages().size(), equalTo(2));
		// LUXURY @TEST could also check each message itself for correctness;
		final Collection<UserAndState> receivedUserMsgs = this.getReceivedUserServiceMessages();
		assertThat(receivedUserMsgs.size(), equalTo(expectedUserMsgs));
//		assertThat("Actual user messages: " + Arrays.toString(rawReceivedUserMessages().toArray()), usReceivedUserMessages().size(), equalTo(expectedUserMsgs));
	}
	
}
