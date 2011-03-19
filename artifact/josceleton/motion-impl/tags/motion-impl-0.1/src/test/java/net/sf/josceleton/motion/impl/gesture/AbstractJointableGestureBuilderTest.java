package net.sf.josceleton.motion.impl.gesture;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

import java.util.Collection;

import net.sf.josceleton.core.api.entity.joint.Joint;
import net.sf.josceleton.core.api.entity.joint.Joints;
import net.sf.josceleton.motion.api.gesture.GestureListener;
import net.sf.josceleton.motion.api.gesture.JointableGesture;
import net.sf.josceleton.motion.api.gesture.JointableGestureBuilder;
import net.sf.josceleton.motion.api.gesture.JointableGestureConfig;

import org.hamcrest.Matchers;
import org.testng.annotations.Test;

@SuppressWarnings("boxing")
public abstract class AbstractJointableGestureBuilderTest<
		B extends JointableGestureBuilder<B, G, C, L>,
		G extends JointableGesture<C, L>,
		C extends JointableGestureConfig,
		L extends GestureListener>
	extends AbstractGestureBuilderTest {
	
	protected abstract AbstractJointableGestureBuilder<B, G, C, L> createTestee();
	
	@Test
	public final void abstractTestPAttachedJoints() {
		final AbstractJointableGestureBuilder<B, G, C, L> testee = this.createTestee();
		final Joint expectedJoint = Joints.HEAD();
		testee.relevantJoint(expectedJoint);
		final Collection<Joint> actualJoints = testee.getPAttachedJoints();
		
		assertThat(actualJoints.size(), equalTo(1));
		assertThat(actualJoints.iterator().next(), Matchers.is(expectedJoint));
	}
}
