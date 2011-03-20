package net.sf.josceleton.connection.api.service.motion;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import net.sf.josceleton.commons.test.jmock.AbstractMockeryTest;
import net.sf.josceleton.connection.api.Connection;
import net.sf.josceleton.connection.api.ConnectionListener;
import net.sf.josceleton.connection.api.test.TestableMotionSupplierListener;
import net.sf.josceleton.core.api.entity.joint.Joint;
import net.sf.josceleton.core.api.entity.joint.Joints;
import net.sf.josceleton.core.api.entity.location.Coordinate;
import net.sf.josceleton.core.api.entity.message.JointMessage;
import net.sf.josceleton.core.api.entity.message.UserMessage;
import net.sf.josceleton.core.api.entity.user.User;
import net.sf.josceleton.core.api.entity.user.UserState;
import net.sf.josceleton.core.api.test.TestableJointMessage;
import net.sf.josceleton.core.api.test.TestableUserMessage;
import net.sf.josceleton.core.impl.entity.joint.SkeletonFactory;
import net.sf.josceleton.core.impl.entity.joint.SkeletonInternal;

import org.jmock.Expectations;
import org.testng.annotations.Test;

@SuppressWarnings("boxing")
public abstract class MotionSupplierTest<M extends MotionSupplier> extends AbstractMockeryTest {
	
	protected abstract M createTestee(Connection connection, SkeletonFactory skeletonFactory);

	protected abstract ConnectionListener asConnectionListener(M testee);

	protected abstract void dispatch(JointMessage message, M testee);
	protected abstract void dispatch(UserMessage message, M testee);
	
	// MINOR @TEST refactor DRY testcode! (creation/preparation of testee)
	
	@Test public final void justEverything() {
		final User user = this.mock(User.class);
		final Joint jointMsg1Joint = Joints.TORSO();
		final Coordinate jointMsg1Coord = this.mock(Coordinate.class);
		final TestableJointMessage jointMsg1 = new TestableJointMessage(user, jointMsg1Joint, jointMsg1Coord);
		final TestableMotionSupplierListener listener = new TestableMotionSupplierListener();
		
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
		
		assertThat(listener.getOnMovedParameters().size(), equalTo(0));
		this.dispatch(jointMsg1, testee);
		assertThat(listener.getOnMovedParameters().size(), equalTo(1));
	}
	
	@Test public final void nothingWillBeDoneOnReceivingUserMessages() {
		final SkeletonFactory skeletonFactory = this.mock(SkeletonFactory.class);
		final Connection connection = this.mock(Connection.class);
		
		final M testee = this.createTestee(connection, skeletonFactory);
		this.dispatch(new TestableUserMessage(this.mock(User.class), UserState.WAITING), testee);
		
	}
	// MINOR @TEST dispatch user message
	// MINOR @TEST add / remove same listener several times
	
	@Test
	public final void addTwoListenersThenRemoveNonAddedShouldHaveNoEffectThenRemovingAllShouldCloseConnection() {
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
			oneOf(connection).removeListener(asConnectionListener(testee)); // after removing last, should remove itself
		}});
		

		final User user = this.mock(User.class);
		final MotionSupplierListener listener1 = this.mock(MotionSupplierListener.class, "listener1");
		final MotionSupplierListener listener2 = this.mock(MotionSupplierListener.class, "listener2");
		final MotionSupplierListener listener3NotAdded = this.mock(MotionSupplierListener.class, "listener3NotAdded");
		
		testee.addListenerFor(user, listener1);
		testee.addListenerFor(user, listener2);
		testee.removeListenerFor(user, listener3NotAdded); // does nothing
		testee.removeListenerFor(user, listener1);
		testee.removeListenerFor(user, listener2);
	}
	
	@Test public final void afterAddingAndRemvoingOneselfOneDoesntGetAnyFurtherMessages() {
		final M testee = this.createSimpleTestee();
		final MotionSupplierListener listener = this.mock(MotionSupplierListener.class); // no expectations confirms test assertion
		final User user = this.mock(User.class);
		testee.addListenerFor(user, listener);
		testee.removeListenerFor(user, listener);
		
		final Joint joint = Joints.TORSO();
		final Coordinate coordinate = this.mock(Coordinate.class);
		final JointMessage mockedMessage = new TestableJointMessage(user, joint, coordinate);
		
		this.dispatch(mockedMessage, testee);
		// listener doesnt receive joint message anymore - its working! (if mock expectations are fullfilled afterwards)
	}
	
	@Test
	public final void addingSameListenerTwiceDoesntDoAnythingCheckedViaMockExpectations() {
		final M testee = this.createSimpleTestee();
		final MotionSupplierListener listener = this.mock(MotionSupplierListener.class);
		final User user = this.mock(User.class);
		testee.addListenerFor(user, listener);
		testee.addListenerFor(user, listener);
		testee.removeListenerFor(user, listener);
	}
	
	@Test
	public final void addAndRemoveSomeListeners() {
		final M testee = this.createSimpleTestee();
		final MotionSupplierListener listener1 = this.mock(MotionSupplierListener.class, "listener1");
		final MotionSupplierListener listener2 = this.mock(MotionSupplierListener.class, "listener2");
		final User user = this.mock(User.class);
		testee.addListenerFor(user, listener1);
		testee.addListenerFor(user, listener2);
		testee.removeListenerFor(user, listener1);
		testee.removeListenerFor(user, listener2);
	}
	
	private M createSimpleTestee() {
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
			oneOf(connection).removeListener(asConnectionListener(testee));
		}});
		return testee;
	}
}
