package net.sf.josceleton.core.api.entity.joint;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import net.sf.josceleton.commons.test.AbstractEqualsTest;
import net.sf.josceleton.commons.test.EqualsDescriptor;
import net.sf.josceleton.commons.test.util.TestUtil;
import net.sf.josceleton.core.api.entity.joint.JointParts.LeftJoint;
import net.sf.josceleton.core.api.entity.joint.JointParts.RightJoint;
import net.sf.josceleton.core.api.entity.joint.JointParts.SymetricJoint;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

public class SymmetricJointTest extends AbstractEqualsTest<SymetricJoint<Joint, LeftJoint<Joint>, RightJoint<Joint>>> {

	@DataProvider(name = "provideSymmetricJointImpls")
	public final Object[][] provideSymmetricJointImpls() {
		return new Object[][] {
				new Object[] { new JointImplProvider.ShouldersImpl() },
				new Object[] { new JointImplProvider.ElbowsImpl() },
				new Object[] { new JointImplProvider.HandsImpl() },
				new Object[] { new JointImplProvider.HipsImpl() },
				new Object[] { new JointImplProvider.KneesImpl() },
				new Object[] { new JointImplProvider.AnklesImpl() },
				new Object[] { new JointImplProvider.FeetImpl() }
		};
	}
	
	@Test(dataProvider = "provideSymmetricJointImpls")
	public final void testSomeJoints(
			final SymetricJoint<Joint, LeftJoint<Joint>, RightJoint<Joint>> joint) {
		assertThat(joint, equalTo(joint));
		TestUtil.assertObjectToString(joint, false, joint.LEFT().toString(), joint.RIGHT().toString());
	}

	@SuppressWarnings("unchecked")
	@Override
	protected final EqualsDescriptor<SymetricJoint<Joint, LeftJoint<Joint>, RightJoint<Joint>>> createEqualsDescriptor() {
		return new EqualsDescriptor(
			new JointImplProvider.HipsImpl(), new JointImplProvider.HipsImpl(),
			
			new JointImplProvider.KneesImpl(), new JointImplProvider.ShouldersImpl(),
			
			new TestableSymetricJoint(new JointImplProvider.HipsImpl().LEFT(),
				new JointImplProvider.HandsImpl().RIGHT()),
				
			new TestableSymetricJoint(new JointImplProvider.HandsImpl().LEFT(),
				new JointImplProvider.HipsImpl().RIGHT())
		);
	}

	@Override
	protected final Object createSameTesteeForEquals() {
		return new JointImplProvider.KneesImpl();
	}
	
	static class TestableSymetricJoint<B extends Joint, L extends LeftJoint<B>, R extends RightJoint<B>>
		implements SymetricJoint<B, L, R> {
		
		private final L left;
		private final R right;
		public TestableSymetricJoint(final L left, final R right) {
			this.left = left;
			this.right = right;
		}
		@Override public L LEFT() {
			return this.left;
		}
		@Override public R RIGHT() {
			return this.right;
		}
		
	}
}
