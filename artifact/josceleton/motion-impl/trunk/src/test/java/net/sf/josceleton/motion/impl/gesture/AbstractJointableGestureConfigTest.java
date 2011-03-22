package net.sf.josceleton.motion.impl.gesture;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

import java.util.Arrays;
import java.util.Collection;

import net.sf.josceleton.commons.test.AbstractEqualsTest;
import net.sf.josceleton.core.api.entity.joint.Joint;
import net.sf.josceleton.core.api.entity.joint.Joints;
import net.sf.josceleton.motion.api.gesture.JointableGestureConfig;

import org.testng.annotations.Test;

public abstract class AbstractJointableGestureConfigTest<C extends JointableGestureConfig> extends AbstractEqualsTest<C> {
	
	protected abstract C newTestee(Collection<Joint> relevantJoints);
	
	@Test
	public final void getterReturnsIdenticalRelevantJoints() {
		final Collection<Joint> relevantJoints = Arrays.asList(Joints.KNEE().LEFT(), Joints.HEAD());
		final C config = this.newTestee(relevantJoints);
		assertThat(config.getRelevantJoints(), is(relevantJoints));
	}
	
}
