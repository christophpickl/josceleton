package net.sf.josceleton.connection.impl.osc;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import net.sf.josceleton.commons.exception.InvalidArgumentException;
import net.sf.josceleton.connection.impl.service.UserStore;
import net.sf.josceleton.core.api.entity.Coordinate;
import net.sf.josceleton.core.api.entity.User;
import net.sf.josceleton.core.api.entity.UserState;
import net.sf.josceleton.core.api.entity.body.Body;
import net.sf.josceleton.core.api.entity.body.BodyPart;
import net.sf.josceleton.core.api.entity.message.JointMessage;
import net.sf.josceleton.core.api.entity.message.UserMessage;
import net.sf.josceleton.core.impl.entity.FactoryFacade;

import com.google.inject.Inject;
import com.illposed.osc.OSCMessage;

@SuppressWarnings("boxing")
class OscMessageTransformerImpl implements OscMessageTransformer {
	
	private static final Map<String, UserState> USER_STATE_BY_ADDRESS;
	static {
		final Map<String, UserState> tmp = new HashMap<String, UserState>();
		tmp.put(OscAddress.NEW_USER.getAddress(), UserState.WAITING);
		tmp.put(OscAddress.NEW_SKEL.getAddress(), UserState.PROCESSING);
		tmp.put(OscAddress.LOST_USER.getAddress(), UserState.DEAD);
		USER_STATE_BY_ADDRESS = Collections.unmodifiableMap(tmp);
	}
	
	private static final Map<String, BodyPart> BODY_PARTS_BY_ID;
	static {
		final Map<String, BodyPart> tmp = new HashMap<String, BodyPart>();
		for (final BodyPart part : Body.values()) {
			tmp.put(part.getOsceletonId(), part);
		}
		BODY_PARTS_BY_ID = Collections.unmodifiableMap(tmp);
	}
	
	
	private final FactoryFacade factory;
	
	@Inject OscMessageTransformerImpl(final FactoryFacade factory) {
		this.factory = factory;
	}
	

	/** {@inheritDoc} from {@link OscMessageTransformer} */
	@Override public final JointMessage transformJointMessage(
			final OSCMessage oscMessage,
			final UserStore userStore) {
		final Object[] messageArgs = oscMessage.getArguments();

		if(messageArgs.length != 5) {
			throw InvalidArgumentException.newInstance("oscMessage.arguments.length", messageArgs.length, "==5");
		}
		
		final String rawBodyJointType = (String) messageArgs[0];
		final BodyPart jointPart = BODY_PARTS_BY_ID.get(rawBodyJointType);
		if(jointPart == null) {
			throw new RuntimeException("Invalid joint ID [" + rawBodyJointType + "]!");
		}
		
		final Integer osceletonUserId = (Integer) messageArgs[1];
		final User user = userStore.lookupUserForJointMessage(osceletonUserId);
		
		final float x = ((Float) messageArgs[2]).floatValue();
		final float y = ((Float) messageArgs[3]).floatValue();
		final float z = ((Float) messageArgs[4]).floatValue();
		final Coordinate coordinate = this.factory.createCoordinate(x, y, z);
		
		return this.factory.createJointMessage(user, jointPart, coordinate);
	}

	/** {@inheritDoc} from {@link OscMessageTransformer} */
	@Override public final UserMessage transformUserMessage(
			final OSCMessage oscMessage,
			final UserStore userStore) {
		
		final Object[] messageArgs = oscMessage.getArguments();

		if(messageArgs.length != 1) {
			throw InvalidArgumentException.newInstance("oscMessage.arguments.length", messageArgs.length, "==1");
		}
		
		final String address = oscMessage.getAddress();
		final UserState userState = USER_STATE_BY_ADDRESS.get(address);
		if(userState == null) {
			throw new RuntimeException("Invalid user message address [" + address + "]!");
		}
		
		final Integer osceletonUserId = (Integer) messageArgs[0];
		final User user = userStore.lookupUserForUserMessage(osceletonUserId, userState);
		System.out.println("xxxxx: " + user);
		return this.factory.createUserMessage(user, userState);
	}

}
