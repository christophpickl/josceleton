package net.sf.josceleton.motion.impl.gesture;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

import java.util.Arrays;
import java.util.Collection;

import net.sf.josceleton.core.api.entity.joint.Joint;
import net.sf.josceleton.core.api.entity.joint.Joints;

import org.testng.annotations.Test;

public abstract class AbstractJointableGestureConfigTest {
	
	protected abstract AbstractJointableGestureConfig newTestee(Collection<Joint> relevantJoints);
	
	@Test
	public final void getterReturnsIdenticalRelevantJoints() {
		final Collection<Joint> relevantJoints = Arrays.asList(Joints.ANKLE().LEFT(), Joints.HEAD());
		final AbstractJointableGestureConfig config = this.newTestee(relevantJoints);
		assertThat(config.getRelevantJoints(), is(relevantJoints));
	}
	
}
