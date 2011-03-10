package net.sf.josceleton.core.impl.entity;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import net.sf.josceleton.commons.test.jmock.AbstractMockeryTest;
import net.sf.josceleton.core.api.entity.Coordinate;
import net.sf.josceleton.core.api.entity.User;
import net.sf.josceleton.core.api.entity.UserState;
import net.sf.josceleton.core.api.entity.body.BodyPart;
import net.sf.josceleton.core.api.entity.message.JointMessage;
import net.sf.josceleton.core.api.entity.message.UserMessage;
import net.sf.josceleton.core.impl.entity.message.JointMessageFactory;
import net.sf.josceleton.core.impl.entity.message.UserMessageFactory;

import org.jmock.Expectations;
import org.testng.annotations.Test;

public class FactoryFacadeImplTest extends AbstractMockeryTest {
	
	@Test
	public final void createProperCoordinate() {
		final float[] coordinates = new float[] { 0.1F, 0.2F, 0.3F };
		final Coordinate mockedCoordinate = this.mock(Coordinate.class);
		
		final CoordinateFactory coordinateFactory = this.newCoordinateFactoryExpects(mockedCoordinate,
			coordinates[0], coordinates[1], coordinates[2]);
		
		final FactoryFacade facade = this.newFacade(coordinateFactory, null, null);
		
		final Coordinate actualCoordinate = facade.createCoordinate(coordinates[0], coordinates[1], coordinates[2]);
		assertThat(actualCoordinate, is(mockedCoordinate));
	}
	
	@Test
	public final void createProperJointMessage() {
		final JointMessage mockedMessage = this.mock(JointMessage.class);

		final User user = this.mock(User.class);
		final BodyPart jointPart = this.mock(BodyPart.class);
		final Coordinate coordinate = this.mock(Coordinate.class);
		
		final JointMessageFactory jointMessageFactory = this.mock(JointMessageFactory.class);
		this.checking(new Expectations() { {
			oneOf(jointMessageFactory).create(user, jointPart, coordinate);
			will(returnValue(mockedMessage));
		}});
		final FactoryFacade facade = this.newFacade(null, jointMessageFactory, null);
		
		final JointMessage actualMessage = facade.createJointMessage(user, jointPart, coordinate);
		assertThat(actualMessage, is(mockedMessage));
	}
	
	@Test
	public final void createProperUserMessage() {
		final UserMessage mockedMessage = this.mock(UserMessage.class);

		final User user = this.mock(User.class);
		final UserState userState = UserState.DEAD;
		
		final UserMessageFactory userMessageFactory = this.mock(UserMessageFactory.class);
		this.checking(new Expectations() { {
			oneOf(userMessageFactory).create(user, userState);
			will(returnValue(mockedMessage));
		}});
		final FactoryFacade facade = this.newFacade(null, null, userMessageFactory);
		
		final UserMessage actualMessage = facade.createUserMessage(user, userState);
		assertThat(actualMessage, is(mockedMessage));
	}
	
	
	private FactoryFacade newFacade(
			final CoordinateFactory optionalCoordinateFactory,
			final JointMessageFactory optionalJointMessageFactory,
			final UserMessageFactory optionalUserMessageFactory) {
		
		final CoordinateFactory coordinateFactory = optionalCoordinateFactory != null ?
			optionalCoordinateFactory : this.mock(CoordinateFactory.class);
		final JointMessageFactory jointMessageFactory = optionalJointMessageFactory != null ?
			optionalJointMessageFactory : this.mock(JointMessageFactory.class);
		final UserMessageFactory userMessageFactory = optionalUserMessageFactory != null ?
			optionalUserMessageFactory : this.mock(UserMessageFactory.class);
		
		return new FactoryFacadeImpl(coordinateFactory, jointMessageFactory, userMessageFactory);
	}
	
	private CoordinateFactory newCoordinateFactoryExpects(final Coordinate mockedCoordinate,
			final float x, final float y, final float z) {
		final CoordinateFactory coordinateFactory = this.mock(CoordinateFactory.class);
		this.checking(new Expectations() { {
			oneOf(coordinateFactory).create(x, y, z);
			will(returnValue(mockedCoordinate));
		}});
		return coordinateFactory;
	}
	
}
