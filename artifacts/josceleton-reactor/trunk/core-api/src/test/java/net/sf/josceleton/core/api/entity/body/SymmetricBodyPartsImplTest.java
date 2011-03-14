package net.sf.josceleton.core.api.entity.body;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import net.sf.josceleton.commons.test.AbstractEqualsTest;
import net.sf.josceleton.commons.test.EqualsDescriptor;
import net.sf.josceleton.commons.test.util.TestUtil;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

public class SymmetricBodyPartsImplTest extends AbstractEqualsTest<SymetricBodyPart<BodyPart, LeftBodyPart<BodyPart>, RightBodyPart<BodyPart>>> {

	@DataProvider(name = "provideSymmetricBodyPartImpls")
	public final Object[][] provideSymmetricBodyPartImpls() {
		return new Object[][] {
				new Object[] { new BodyPartImplProvider.ShouldersImpl() },
				new Object[] { new BodyPartImplProvider.ElbowsImpl() },
				new Object[] { new BodyPartImplProvider.HandsImpl() },
				new Object[] { new BodyPartImplProvider.HipsImpl() },
				new Object[] { new BodyPartImplProvider.KneesImpl() },
				new Object[] { new BodyPartImplProvider.AnklesImpl() },
				new Object[] { new BodyPartImplProvider.FeetImpl() }
		};
	}
	
	@Test(dataProvider = "provideSymmetricBodyPartImpls")
	public final void testSomeParts(
			final SymetricBodyPart<BodyPart, LeftBodyPart<BodyPart>, RightBodyPart<BodyPart>> part) {
		assertThat(part, equalTo(part));
		TestUtil.assertObjectToString(part, false, part.LEFT().toString(), part.RIGHT().toString());
	}

	@SuppressWarnings("unchecked")
	@Override
	protected final EqualsDescriptor<SymetricBodyPart<BodyPart, LeftBodyPart<BodyPart>, RightBodyPart<BodyPart>>> createEqualsDescriptor() {
		return new EqualsDescriptor(
			new BodyPartImplProvider.HipsImpl(), new BodyPartImplProvider.HipsImpl(),
			
			new BodyPartImplProvider.KneesImpl(), new BodyPartImplProvider.ShouldersImpl(),
			
			new TestableSymetricBodyPart(new BodyPartImplProvider.HipsImpl().LEFT(),
				new BodyPartImplProvider.HandsImpl().RIGHT()),
				
			new TestableSymetricBodyPart(new BodyPartImplProvider.HandsImpl().LEFT(),
				new BodyPartImplProvider.HipsImpl().RIGHT())
		);
	}

	@Override
	protected final Object createSameTesteeForEquals() {
		return new BodyPartImplProvider.KneesImpl();
	}
	
	static class TestableSymetricBodyPart<B extends BodyPart, L extends LeftBodyPart<B>, R extends RightBodyPart<B>>
		implements SymetricBodyPart<B, L, R> {
		
		private final L left;
		private final R right;
		public TestableSymetricBodyPart(final L left, final R right) {
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
