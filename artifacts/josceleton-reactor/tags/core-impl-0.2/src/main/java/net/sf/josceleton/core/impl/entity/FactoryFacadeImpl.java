package net.sf.josceleton.core.impl.entity;

import net.sf.josceleton.core.api.entity.Coordinate;
import net.sf.josceleton.core.api.entity.User;
import net.sf.josceleton.core.api.entity.UserState;
import net.sf.josceleton.core.api.entity.body.BodyPart;
import net.sf.josceleton.core.api.entity.message.JointMessage;
import net.sf.josceleton.core.api.entity.message.UserMessage;
import net.sf.josceleton.core.impl.entity.message.JointMessageFactory;
import net.sf.josceleton.core.impl.entity.message.UserMessageFactory;

import com.google.inject.Inject;

class FactoryFacadeImpl implements FactoryFacade {
	
	private final CoordinateFactory coordinateFactory;
	
	private final JointMessageFactory jointMessageFactory;
	
	private final UserMessageFactory userMessageFactory;
	
	
	@Inject FactoryFacadeImpl(
			final CoordinateFactory coordinateFactory,
			final JointMessageFactory jointMessageFactory,
			final UserMessageFactory userMessageFactory) {
		this.coordinateFactory = coordinateFactory;
		this.jointMessageFactory = jointMessageFactory;
		this.userMessageFactory = userMessageFactory;
	}


	/** {@inheritDoc} from {@link FactoryFacade} */
	@Override public final Coordinate createCoordinate(final float x, final float y, final float z) {
		return this.coordinateFactory.create(x, y, z);
	}

	/** {@inheritDoc} from {@link FactoryFacade} */
	@Override public final JointMessage createJointMessage(final User user, final BodyPart jointPart,
			final Coordinate coordinate) {
		return this.jointMessageFactory.create(user, jointPart, coordinate);
	}

	/** {@inheritDoc} from {@link FactoryFacade} */
	@Override public final UserMessage createUserMessage(final User user, final UserState userState) {
		return this.userMessageFactory.create(user, userState);
	}

}
