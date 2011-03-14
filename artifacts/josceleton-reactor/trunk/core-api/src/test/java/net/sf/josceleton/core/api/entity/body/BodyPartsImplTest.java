package net.sf.josceleton.core.api.entity.body;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import net.sf.josceleton.commons.test.AbstractEqualsTest;
import net.sf.josceleton.commons.test.EqualsDescriptor;
import net.sf.josceleton.commons.test.util.TestUtil;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

public class BodyPartsImplTest extends AbstractEqualsTest<BodyPart> {

	@DataProvider(name = "provideBodyPartImpls")
	public final Object[][] provideBodyPartImpls() {
		return new Object[][] {
				new Object[] { new BodyPartImplProvider.HeadImpl() },
				new Object[] { new BodyPartImplProvider.NeckImpl() },
				new Object[] { new BodyPartImplProvider.TorsoImpl() }
		};
	}
	
	@Test(dataProvider = "provideBodyPartImpls")
	public final void testSomeParts(BodyPart part) {
		assertThat(part, equalTo(part));
		TestUtil.assertObjectToString(part, false, part.getLabel());
	}

	@Override
	protected EqualsDescriptor<BodyPart> createEqualsDescriptor() {
		return new EqualsDescriptor<BodyPart>(new BodyPartImplProvider.HeadImpl(), new BodyPartImplProvider.HeadImpl(),
				new BodyPartImplProvider.NeckImpl(), new BodyPartImplProvider.TorsoImpl(),
				new TestableBodyPart("Head", "other_than_head"),
				new TestableBodyPart("Other than head", "head")
		);
	}

	@Override protected BodyPart createSameTesteeForEquals() {
		return new BodyPartImplProvider.HeadImpl();
	}
	
	static class TestableBodyPart implements BodyPart {
		private final String label;
		private final String osceletonId;
		public TestableBodyPart(String label, String osceletonId) {
			this.label = label;
			this.osceletonId = osceletonId;
		}
		@Override
		public String getLabel() {
			return this.label;
		}

		@Override
		public String getOsceletonId() {
			return this.osceletonId;
		}
		
	}
}
