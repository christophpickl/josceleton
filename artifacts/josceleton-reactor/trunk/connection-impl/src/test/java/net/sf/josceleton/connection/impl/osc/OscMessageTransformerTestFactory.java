package net.sf.josceleton.connection.impl.osc;

import net.sf.josceleton.connection.impl.service.user.UserStore;
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
public class OscMessageTransformerTestFactory {

	public final OscMessageTransformer newUserTransformer(
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
				mockery, userStore, userId, jointMessage, jointCoordinates, jointPart, actualMessage, userState
		);
	}
	
	public final OscMessageTransformer newJointTransformer(
			final Mockery mockery,
			final UserStore userStore,
			final float[] actualCoordinates,
			final BodyPart actualJointPart,
			final Integer userId,
			final JointMessage actualMessage
	) {
		final UserState userState = null;
		final UserMessage userMessage = null;
		
		return this.newGenericTransformer(
				mockery, userStore, userId, actualMessage, actualCoordinates, actualJointPart,
				userMessage, userState
		);
	}
	
	private /* private */ OscMessageTransformer newGenericTransformer(
			final Mockery mockery, final UserStore userStore, final Integer userId,
			final JointMessage jointMessage, final float[] coordinates, final BodyPart jointPart,
			final UserMessage userMessage, final UserState userState
	) {
		final boolean isForJointMessage = null != jointPart; // != jointMessage != coordinates
		final FactoryFacade factory = mockery.mock(FactoryFacade.class);
		final User user = mockery.mock(User.class);
		mockery.checking(new Expectations() { { // ************ mock user lookup
			if(isForJointMessage == true) { oneOf(userStore).lookupUserForJointMessage(userId);
			} else { oneOf(userStore).lookupUserForUserMessage(userId, userState); }
			will(returnValue(user));
		}});
		final Coordinate coordinate; // ************ mock coordinate creation
		if(isForJointMessage == true) {
			coordinate = mockery.mock(Coordinate.class);
			mockery.checking(new Expectations() { {
				oneOf(factory).createCoordinate(coordinates[0], coordinates[1], coordinates[2]);
				will(returnValue(coordinate));
			}});
		} else { coordinate = null; /* not used */ }
		mockery.checking(new Expectations() { { // ************ mock message creation
			final Object actualMessage;
			if(isForJointMessage == true) {
				oneOf(factory).createJointMessage(user, jointPart, coordinate);
				actualMessage = jointMessage;
			} else { oneOf(factory).createUserMessage(user, userState); actualMessage = userMessage; }
			will(returnValue(actualMessage));
		}});
		return new OscMessageTransformerImpl(factory);
	}
}
