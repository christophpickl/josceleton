package net.sf.josceleton.core.api.entity;

import static net.sf.josceleton.commons.test.matcher.JosceletonMatchers.hasSinglePrivateNullifiedConstructorAndInvokeIt;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import net.sf.josceleton.commons.test.util.TestUtil;
import net.sf.josceleton.core.api.entity.joint.Joint;
import net.sf.josceleton.core.api.entity.joint.Joints;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

@SuppressWarnings("boxing")
public class BodyTest {
	
	@Test
	public final void hasSinglePrivateNullifiedConstructor() {
		assertThat(Joints.class, hasSinglePrivateNullifiedConstructorAndInvokeIt());
	}
	
	@Test
	public final void valuesCountShouldBeEqualsTo17() {
		final Iterable<Joint> joints = Joints.values();
		final int jointsCount = TestUtil.countIterable(joints);
		assertThat(jointsCount, equalTo(17));
	}
	
	@DataProvider(name = "provideJointOsceletonId")
	public final Object[][] provideJointOsceletonId() {
		return new Object[][] {
			new Object[] { Joints.HEAD(), "head" },
			new Object[] { Joints.NECK(), "neck" },
			new Object[] { Joints.TORSO(), "torso" },
			new Object[] { Joints.SHOULDER().LEFT(), "l_shoulder" },
			new Object[] { Joints.SHOULDER().RIGHT(), "r_shoulder" },
			new Object[] { Joints.ELBOW().LEFT(), "l_elbow" },
			new Object[] { Joints.ELBOW().RIGHT(), "r_elbow" },
			new Object[] { Joints.HAND().LEFT(), "l_hand" },
			new Object[] { Joints.HAND().RIGHT(), "r_hand" },
			new Object[] { Joints.HIP().LEFT(), "l_hip" },
			new Object[] { Joints.HIP().RIGHT(), "r_hip" },
			new Object[] { Joints.KNEE().LEFT(), "l_knee" },
			new Object[] { Joints.KNEE().RIGHT(), "r_knee" },
			new Object[] { Joints.ANKLE().LEFT(), "l_ankle" },
			new Object[] { Joints.ANKLE().RIGHT(), "r_ankle" },
			new Object[] { Joints.FOOT().LEFT(), "l_foot" },
			new Object[] { Joints.FOOT().RIGHT(), "r_foot" }
		};
	}
	@Test(dataProvider = "provideJointOsceletonId")
	public final void checkProperJointOsceletonId(final Joint joint, final String osceletonId) {
		assertThat(joint.getOsceletonId(), equalTo(osceletonId));
	}
	
	@Test
	public final void checkSomeJointLabels() {
		assertThat(Joints.HEAD().getLabel(), equalTo("Head"));
		assertThat(Joints.HAND().LEFT().getLabel(), equalTo("Left Hand"));
		// okay, we are done ;)
		
	}
	
	
}
