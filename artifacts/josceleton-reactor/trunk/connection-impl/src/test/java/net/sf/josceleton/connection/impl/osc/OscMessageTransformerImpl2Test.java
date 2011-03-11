package net.sf.josceleton.connection.impl.osc;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.sameInstance;
import net.sf.josceleton.commons.test.jmock.AbstractMockeryTest;
import net.sf.josceleton.connection.impl.service.UserStore;
import net.sf.josceleton.connection.impl.test.OSCMessageX;
import net.sf.josceleton.core.api.entity.UserState;
import net.sf.josceleton.core.api.entity.body.Body;
import net.sf.josceleton.core.api.entity.body.BodyPart;
import net.sf.josceleton.core.api.entity.message.JointMessage;
import net.sf.josceleton.core.api.entity.message.UserMessage;

import org.testng.annotations.Test;

import com.illposed.osc.OSCMessage;

/**
 * @since 0.3
 */
@SuppressWarnings("boxing")
public class OscMessageTransformerImpl2Test extends AbstractMockeryTest {

	// MINOR @TEST ENHANCE create dataproviders for OscMessageTransformer tests
	
	@Test public final void transformUserMessageProperly() {
		// transformer got internally static mapping USER_STATE_BY_ADDRESS
		this.assertTransformUserMessageProperly(3, "/lost_user", UserState.DEAD);
	}

	private void assertTransformUserMessageProperly(
			final Integer osceletonUserId,
			final String messageAddress,
			final UserState userState) {
		final UserMessage expectedMessage = this.mock(UserMessage.class);
		final UserStore userStore = this.mock(UserStore.class);
		final OscMessageTransformer transformer = this.newUserTransformer(userStore, osceletonUserId, userState, expectedMessage);
		final OSCMessage oscMessage = OSCMessageX.newMockSafeAddressAndArgs(this.getMockery(), messageAddress, osceletonUserId);
		
		final UserMessage actualMessage = transformer.transformUserMessage(oscMessage, userStore);
		assertThat(actualMessage, is(sameInstance(expectedMessage)));
	}
	
	@Test public final void transformJointMessageProperly() {
		this.assertTransformJointMessageProperly(3, Body.HEAD(), new float[] { 0.4F, 0.2F, 1.3F } );
	}
	
	private void assertTransformJointMessageProperly(
			final Integer actualOsceletonUserId,
			final BodyPart actualJointPart,
			final float[] coordinates
			) {
		final OSCMessage oscMessage = OSCMessageX.newMockSafeArguments(this.getMockery(),
				actualJointPart.getOsceletonId(), actualOsceletonUserId, coordinates[0], coordinates[1], coordinates[2]);
		final JointMessage expectedMessage = this.mock(JointMessage.class);
		final UserStore userStore = this.mock(UserStore.class);
		
		final OscMessageTransformer transformer = this.newJointTransformer(userStore, coordinates, actualJointPart, actualOsceletonUserId, expectedMessage);
		final JointMessage actualMessage = transformer.transformJointMessage(oscMessage, userStore);

		assertThat(actualMessage, is(sameInstance(expectedMessage)));
	}
	
	
	

	private OscMessageTransformer newUserTransformer(
			final UserStore userStore,
			final Integer userId,
			final UserState userState,
			final UserMessage actualMessage) {
		return new OscMessageTransformerFactory().newUserTransformer(this.getMockery(),
			userStore, userId, userState, actualMessage);
	}
	
	private OscMessageTransformer newJointTransformer(
			final UserStore userStore,
			final float[] actualCoordinates,
			final BodyPart actualJointPart,
			final Integer actualOsceletonUserId,
			final JointMessage actualMessage) {
		return new OscMessageTransformerFactory().newJointTransformer(this.getMockery(),
				userStore, actualCoordinates, actualJointPart, actualOsceletonUserId, actualMessage);
	}
	
}
