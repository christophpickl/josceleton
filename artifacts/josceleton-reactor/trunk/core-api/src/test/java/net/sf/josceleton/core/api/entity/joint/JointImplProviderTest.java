package net.sf.josceleton.core.api.entity.joint;

import static net.sf.josceleton.commons.test.matcher.JosceletonMatchers.hasSinglePrivateNullifiedConstructorAndInvokeIt;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import net.sf.josceleton.commons.test.AbstractEqualsTest;
import net.sf.josceleton.commons.test.EqualsDescriptor;
import net.sf.josceleton.commons.test.util.TestUtil;
import net.sf.josceleton.core.api.entity.joint.Joint;
import net.sf.josceleton.core.api.entity.joint.JointImplProvider;
import net.sf.josceleton.core.api.entity.joint.Joint;
import net.sf.josceleton.core.api.entity.joint.JointImplProvider;
import net.sf.josceleton.core.api.entity.joint.JointImplProvider.HeadImpl;
import net.sf.josceleton.core.api.entity.joint.JointImplProvider.NeckImpl;
import net.sf.josceleton.core.api.entity.joint.JointImplProvider.TorsoImpl;
import net.sf.josceleton.core.api.test.TestableJoint;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

public class JointImplProviderTest extends AbstractEqualsTest<Joint> {

	@Test public final void hasSinglePrivateNullifiedConstructor() {
		assertThat(JointImplProvider.class, hasSinglePrivateNullifiedConstructorAndInvokeIt());
	}
	
	@DataProvider(name = "provideJointImpls")
	public final Object[][] provideJointImpls() {
		return new Object[][] {
				new Object[] { new JointImplProvider.HeadImpl() },
				new Object[] { new JointImplProvider.NeckImpl() },
				new Object[] { new JointImplProvider.TorsoImpl() }
		};
	}
	
	@Test(dataProvider = "provideJointImpls")
	public final void testSomeJoints(final Joint joint) {
		assertThat(joint, equalTo(joint));
		TestUtil.assertObjectToString(joint, false, joint.getLabel());
	}

	@Override protected final EqualsDescriptor<Joint> createEqualsDescriptor() {
		return new EqualsDescriptor<Joint>(new JointImplProvider.HeadImpl(), new JointImplProvider.HeadImpl(),
				new JointImplProvider.NeckImpl(), new JointImplProvider.TorsoImpl(),
				new TestableJoint("Head", "other_than_head"),
				new TestableJoint("Other than head", "head")
		);
	}

	@Override protected final Joint createSameTesteeForEquals() {
		return new JointImplProvider.HeadImpl();
	}
	
}
