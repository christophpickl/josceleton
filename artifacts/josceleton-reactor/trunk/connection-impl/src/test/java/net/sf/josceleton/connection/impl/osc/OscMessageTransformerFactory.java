package net.sf.josceleton.connection.impl.osc;

import net.sf.josceleton.connection.impl.service.UserStore;
import net.sf.josceleton.core.api.entity.Coordinate;
import net.sf.josceleton.core.api.entity.User;
import net.sf.josceleton.core.api.entity.UserState;
import net.sf.josceleton.core.api.entity.body.BodyPart;
import net.sf.josceleton.core.api.entity.message.JointMessage;
import net.sf.josceleton.core.api.entity.message.UserMessage;
import net.sf.josceleton.core.impl.entity.FactoryFacade;

import org.jmock.Expectations;
import org.jmock.Mockery;

/**
 * impl will invoke:
 * 
 * 1. user = userStore.lookupUserForJoint/UserMessage(osceletonUserId);
 * 2. coord (optional) = this.factory.createCoordinate(x, y, z);
 * 3. msg = this.factory.createJoint/UserMessage(user, ...);
 * 
 * @since 0.3
 */
public class OscMessageTransformerFactory {

	public OscMessageTransformer newUserTransformer(
			final Mockery mockery,
			final UserStore userStore,
			final Integer userId,
			final UserState userState,
			final UserMessage actualMessage
	) {
		final float[] jointCoordinates = null;
		final BodyPart jointPart = null;
		final JointMessage jointMessage = null;
		
		return this.newGenericTransformer(
				mockery, userStore, jointCoordinates, jointPart, userId, jointMessage, userState, actualMessage
		);
	}
	
	public OscMessageTransformer newJointTransformer(
			final Mockery mockery,
			final UserStore userStore,
			final float[] actualCoordinates,
			final BodyPart actualJointPart,
			final Integer actualOsceletonUserId,
			final JointMessage actualMessage
	) {
		final UserState userState = null;
		final UserMessage userMessage = null;
		
		return this.newGenericTransformer(
				mockery, userStore, actualCoordinates, actualJointPart, actualOsceletonUserId, actualMessage, userState, userMessage
		);
	}
	
	private /* private */ OscMessageTransformer newGenericTransformer(
			final Mockery mockery,
			final UserStore userStore,
			final float[] coordinates,
			final BodyPart jointPart,
			final Integer userId,
			final JointMessage jointMessage,
			final UserState userState,
			final UserMessage userMessage
	) {
		final boolean isForJointMessage = null != jointPart; // != jointMessage != coordinates
		
		final FactoryFacade factory = mockery.mock(FactoryFacade.class);

		// ************ mock user lookup
		final User user = mockery.mock(User.class);
		mockery.checking(new Expectations() {{
			if(isForJointMessage == true) {
				oneOf(userStore).lookupUserForJointMessage(userId);
			} else {
				oneOf(userStore).lookupUserForUserMessage(userId, userState);
			}
			will(returnValue(user));
		}});

		// ************ mock coordinate creation
		final Coordinate coordinate;
		if(isForJointMessage == true) {
			coordinate = mockery.mock(Coordinate.class);
			mockery.checking(new Expectations() {{
				oneOf(factory).createCoordinate(coordinates[0], coordinates[1], coordinates[2]);
				will(returnValue(coordinate));
			}});
		} else {
			coordinate = null; // not used
		}
		
		// ************ mock message creation
		mockery.checking(new Expectations() {{
			final Object actualMessage;
			if(isForJointMessage == true) {
				oneOf(factory).createJointMessage(user, jointPart, coordinate);
				actualMessage = jointMessage;
			} else {
				oneOf(factory).createUserMessage(user, userState);
				actualMessage = userMessage;
			}
			will(returnValue(actualMessage));
		}});
		
		return new OscMessageTransformerImpl(factory);
	}
}
