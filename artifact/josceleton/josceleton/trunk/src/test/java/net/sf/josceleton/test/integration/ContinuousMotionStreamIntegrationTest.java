package net.sf.josceleton.test.integration;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import net.sf.josceleton.connection.api.service.motion.ContinuousMotionStream;
import net.sf.josceleton.connection.api.test.TestableMotionStreamListener;
import net.sf.josceleton.core.api.entity.joint.Joint;
import net.sf.josceleton.core.api.entity.joint.Joints;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

@SuppressWarnings("boxing")
public class ContinuousMotionStreamIntegrationTest extends AbstractIntegrationTest<ContinuousMotionStreamIntegrationTest> {
	
	private static final Joint ALWAYS_MOVING_SAME_JOINT = Joints.HEAD();

	private ContinuousMotionStream testee;
	
	private TestableMotionStreamListener testeeListener;
	
	private int expectedTesteeReceivedMoves;
	
	
	@BeforeMethod public final void setUpState() {
		this.testee = this.getContinuousMotionStream();
		this.testeeListener = new TestableMotionStreamListener();
		this.testee.addListener(this.testeeListener);
		
		this.expectedTesteeReceivedMoves = 0;
	}
	
	@AfterMethod public final void tearDownState() {
		this.testee = null;
	}
	
	@Test public final void continuousMotionStreamWorksJustAsExpected() {
		// regular start case
		login(1).move(1).andExpectedReceived("fresh user 1 moved");
		
		// movement of some other user should be ignored, when still attached to a(n available) user
		login(2).move(2).andExpectedNotReceived("new user 2 ignored");
		
		// attached user lost, testee should reattach to next available user
		logout(1).move(2).andExpectedReceived("lost u1, attaching to u2");
		
		// regulare case; loosing current user, and another moves after that
		logout(2).login(3).move(3).andExpectedReceived("lost u2, new u3 moved");
	}
	
	@Test
	public final void ifMultipleUsersAreAvailableAfterLoosingCurrentOneTheOldestOtherUserShouldBeUsed() {
		login(1).move(1).andExpectedReceived("u1 moved");
		login(40).move(40).andExpectedNotReceived("ignoring u40");
		login(41).move(41).andExpectedNotReceived("ignoring u41");
		login(42).move(42).andExpectedNotReceived("ignoring u42");
		
		logout(1);
		move(40).andExpectedReceived("reattached to u40");
		move(41).andExpectedNotReceived("ignoring younger u41");
		move(42).andExpectedNotReceived("ignoring younger u42");
	}
	
	private ContinuousMotionStreamIntegrationTest move(final int userId) {
		this.dispatchJointMessage(userId, ALWAYS_MOVING_SAME_JOINT);
		return this;
	}

	private void andExpectedReceived(final String assertMessage) {
		this.expectedTesteeReceivedMoves++;
		this.assertReceivedMoveMessages(assertMessage);
	}

	private void andExpectedNotReceived(final String assertMessage) {
		this.assertReceivedMoveMessages(assertMessage);
	}
	
	private void assertReceivedMoveMessages(final String assertMessage) {
		assertThat("Received move messages from testee was incorrect: " + assertMessage,
			this.testeeListener.getOnMovedParameters().size(), equalTo(this.expectedTesteeReceivedMoves));
	}
	
}
