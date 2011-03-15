package net.sf.josceleton.connection.api.service.motion;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import net.sf.josceleton.commons.test.jmock.AbstractMockeryTest;
import net.sf.josceleton.connection.api.Connection;
import net.sf.josceleton.connection.api.ConnectionListener;
import net.sf.josceleton.connection.api.test.TestableMotionSeparatorListener;
import net.sf.josceleton.core.api.entity.Coordinate;
import net.sf.josceleton.core.api.entity.User;
import net.sf.josceleton.core.api.entity.body.Body;
import net.sf.josceleton.core.api.entity.body.BodyPart;
import net.sf.josceleton.core.api.entity.body.BodyParts.Torso;
import net.sf.josceleton.core.api.entity.message.JointMessage;
import net.sf.josceleton.core.api.entity.message.UserMessage;
import net.sf.josceleton.core.api.test.TestableJointMessage;
import net.sf.josceleton.core.impl.entity.body.SkeletonFactory;
import net.sf.josceleton.core.impl.entity.body.SkeletonInternal;

import org.jmock.Expectations;
import org.testng.annotations.Test;

@SuppressWarnings("boxing")
public abstract class MotionSeparatorTest<M extends MotionSeparator> extends AbstractMockeryTest {
	
	protected abstract M createTestee(Connection connection, SkeletonFactory skeletonFactory);

	protected abstract ConnectionListener asConnectionListener(M testee);

	protected abstract void dispatch(JointMessage message, M testee);
	protected abstract void dispatch(UserMessage message, M testee);
	
	@Test public final void justEverything() {
		final User user = this.mock(User.class);
		final Torso jointMsg1Joint = Body.TORSO();
		final Coordinate jointMsg1Coord = this.mock(Coordinate.class);
		final TestableJointMessage jointMsg1 = new TestableJointMessage(user, jointMsg1Joint, jointMsg1Coord);
		final TestableMotionSeparatorListener listener = new TestableMotionSeparatorListener();
		
		final Connection connection = this.mock(Connection.class);
		final SkeletonInternal skeleton = this.mock(SkeletonInternal.class);
		this.checking(new Expectations() { {
			oneOf(skeleton).update(jointMsg1Joint, jointMsg1Coord);
		}});
		
		final SkeletonFactory skeletonFactory = this.mock(SkeletonFactory.class);
		this.checking(new Expectations() { {
			oneOf(skeletonFactory).create();
			will(returnValue(skeleton));
		}});
		
		final M testee = this.createTestee(connection, skeletonFactory);
		this.checking(new Expectations() { {
			// LUXURY @TEST hack: breaking up implementation ;)
			oneOf(connection).addListener((ConnectionListener) testee);
		}});
		testee.addListenerFor(user, listener);
		
		assertThat(listener.getOnMovedInvocations().size(), equalTo(0));
		this.dispatch(jointMsg1, testee);
		assertThat(listener.getOnMovedInvocations().size(), equalTo(1));
	}
	
	// TODO TEST dispatch user message
	// TODO TEST add / remove same listener several times
	
	@Test public final void nothingWillBeDoneOnReceivingUserMessages() {
		final SkeletonInternal skeleton = this.mock(SkeletonInternal.class);
		final SkeletonFactory skeletonFactory = this.mock(SkeletonFactory.class);
		this.checking(new Expectations() { {
			oneOf(skeletonFactory).create();
			will(returnValue(skeleton));
		}});
		
		final Connection connection = this.mock(Connection.class);
		final M testee = this.createTestee(connection, skeletonFactory);
		this.checking(new Expectations() { {
			oneOf(connection).addListener(asConnectionListener(testee));
		}});
		
		final MotionSeparatorListener listener = this.mock(MotionSeparatorListener.class);
		final User user = this.mock(User.class);
		testee.addListenerFor(user, listener);
		testee.removeListenerFor(user, listener);
		
		final BodyPart joint = Body.TORSO();
		final Coordinate coordinate = this.mock(Coordinate.class);
		final JointMessage mockedMessage = new TestableJointMessage(user, joint, coordinate);
		this.checking(new Expectations() { {
			oneOf(skeleton).update(joint, coordinate);
		}});
		
		this.dispatch(mockedMessage, testee);
		// listener doesnt receive joint message anymore - its working! (if mock expectations are fullfilled afterwards)
	}
}
