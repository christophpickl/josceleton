package net.sf.josceleton.connection.impl.osc;

import net.sf.josceleton.commons.exception.InvalidArgumentException;
import net.sf.josceleton.commons.test.jmock.AbstractMockeryTest;
import net.sf.josceleton.connection.impl.test.OSCMessageX;
import net.sf.josceleton.core.api.entity.Coordinate;
import net.sf.josceleton.core.api.entity.User;
import net.sf.josceleton.core.api.entity.UserState;
import net.sf.josceleton.core.api.entity.body.Body;
import net.sf.josceleton.core.api.entity.body.BodyPart;
import net.sf.josceleton.core.api.entity.message.JointMessage;
import net.sf.josceleton.core.api.entity.message.UserMessage;
import net.sf.josceleton.core.impl.entity.FactoryFacade;
import net.sf.josceleton.core.impl.entity.UserFactory;

import org.jmock.Expectations;
import org.testng.annotations.Test;

import com.illposed.osc.OSCMessage;

@SuppressWarnings("boxing")
public class OscMessageTransformerImplTest extends AbstractMockeryTest {

	@Test(expectedExceptions = InvalidArgumentException.class,
			expectedExceptionsMessageRegExp = ".*arguments.length.*3.*")
	public final void tooLessJointOscMessageArgumentsFails() {
		final OscMessageTransformer transformer = this.newSimpleTransformer();
		final OSCMessage oscMessage = OSCMessageX.newMockSafeOSCMessage(this.getMockery(), new Object[] { 1, 2, 3 });
		transformer.transformJointMessage(oscMessage);
	}
	
	@Test(expectedExceptions = InvalidArgumentException.class,
			expectedExceptionsMessageRegExp = "Passed illegal argument \\[oscMessage.arguments.length\\] with value: " +
					"\\[0\\]! \\(condition was: ==1\\)")
	public final void tooLessUscOscMessageArgumentsFails() {
		final OscMessageTransformer transformer = this.newSimpleTransformer();
		final OSCMessage oscMessage = OSCMessageX.newMockSafeOSCMessage(this.getMockery(), new Object[] { });
		transformer.transformUserMessage(oscMessage);
	}
	
	private OscMessageTransformer newSimpleTransformer() {
		final FactoryFacade factory = this.mock(FactoryFacade.class);
		final UserFactory userFactory = this.mock(UserFactory.class);
		return new OscMessageTransformerImpl(factory, userFactory);
	}
	

	
	
	@Test
	public final void transformJointMessageProperly() {
		final Integer osceletonUserId = Integer.valueOf(3);
		final BodyPart jointPart = Body.HEAD();
		final float[] coorindates = new float[] { 0.4F, 0.2F, 1.3F };
		
		final OscMessageTransformer transformer = this.newTransformer(osceletonUserId, jointPart, null, coorindates);
		final OSCMessage message = this.newOSCMessage(osceletonUserId, jointPart.getOsceletonId(), null, coorindates);
		transformer.transformJointMessage(message);
		// asserts via mockery-is-satisfied
	}

	@Test
	public final void transformUserMessageProperly() {
		final Integer osceletonUserId = Integer.valueOf(3);
		final UserState state = UserState.DEAD;
		final String messageAddress = "/lost_user";
		final float[] coorindates = new float[] { 0.0F, 0.0F, 0.0F };
		
		final OscMessageTransformer transformer = this.newTransformer(osceletonUserId, null, state, coorindates);
		final OSCMessage oscMessage = this.newOSCMessage(osceletonUserId, null, messageAddress, coorindates);
		
		transformer.transformUserMessage(oscMessage);
		// asserts via mockery-is-satisfied
	}

	@Test(expectedExceptions = RuntimeException.class,
			expectedExceptionsMessageRegExp = "Invalid joint ID \\[x_hand\\]!")
	public final void transformJointMessageInvalidBodyPartIdFails() {
		final float[] unusedCoorindates = new float[] { 0.0F, 0.0F, 0.0F };
		final Integer osceletonUserId = Integer.valueOf(3);
		final OscMessageTransformer transformer = this.newTransformer(osceletonUserId, null, null, unusedCoorindates);
		final OSCMessage oscMessage = this.newOSCMessage(osceletonUserId, "x_hand", null, unusedCoorindates);
		transformer.transformJointMessage(oscMessage);
	}

	@Test(expectedExceptions = RuntimeException.class,
			expectedExceptionsMessageRegExp = "Invalid user message address \\[\\/invalid\\]!")
	public final void transformUserMessageInvalidAddressFails() {
		final float[] unusedCoorindates = new float[] { 0.0F, 0.0F, 0.0F };
		final Integer osceletonUserId = Integer.valueOf(3);
		final OscMessageTransformer transformer = this.newTransformer(osceletonUserId, null, null, unusedCoorindates);
		final OSCMessage oscMessage = this.newOSCMessage(osceletonUserId, null, "/invalid", unusedCoorindates);
		transformer.transformUserMessage(oscMessage);
	}
	
	private OSCMessage newOSCMessage(final Integer osceletonUserId, final String osceletonJointId,
			final String userState, final float [] coordinates) {
		final OSCMessage oscMessage = this.mock(OSCMessage.class);
		final Object[] oscArguments;
		if(osceletonJointId != null) {
			oscArguments = new Object[] { osceletonJointId, osceletonUserId,
					coordinates[0], coordinates[1], coordinates[2] };
		} else {
			oscArguments = new Object[] { osceletonUserId };
		}
		this.checking(new Expectations() { {
			oneOf(oscMessage).getArguments();
			will(returnValue(oscArguments));
			
			if(userState != null) {
				oneOf(oscMessage).getAddress();
				will(returnValue(userState));
			}
		}});
		return oscMessage;
	}
	
	private OscMessageTransformer newTransformer(final Integer osceletonUserId, final BodyPart jointPart,
			final UserState userState, final float[] coordinates) {
		final User user = this.mock(User.class);
		final UserFactory userFactory = this.mock(UserFactory.class);
		if(jointPart != null || userState != null) {
			this.checking(new Expectations() { {
//				if(jointPart != null) {
//					oneOf(userStore).retrieveUserForJointMessage(osceletonUserId);
//				} else if(userState != null) {
//					oneOf(userStore).retrieveUserForUserMessage(osceletonUserId, userState);
//				}
				oneOf(userFactory).create(with(any(int.class)), with(osceletonUserId.intValue()));
				will(returnValue(user));
			}});
		}

		final FactoryFacade factory = this.newFactoryFacade(user, jointPart, userState, coordinates);
		
		return new OscMessageTransformerImpl(factory, userFactory);
	}
	
	private FactoryFacade newFactoryFacade(final User user, final BodyPart jointPart, final UserState userState,
			final float[] coordinates) {
		
		final Coordinate coordinate = this.mock(Coordinate.class);
		
		final JointMessage jointMessage = this.mock(JointMessage.class);
		final UserMessage userMessage = this.mock(UserMessage.class);
		
		final FactoryFacade factory = this.mock(FactoryFacade.class);
		if(jointPart != null || userState != null) {
			this.checking(new Expectations() { {
				if(jointPart != null) {
					oneOf(factory).createCoordinate(coordinates[0], coordinates[1], coordinates[2]);
					will(returnValue(coordinate));
				}
				
				if(jointPart != null) {
					oneOf(factory).createJointMessage(user, jointPart, coordinate);
					will(returnValue(jointMessage));
				} else if(userState != null) {
					oneOf(factory).createUserMessage(user, userState);
					will(returnValue(userMessage));
				}
			}});
		}
		return factory;
	}
	
}
