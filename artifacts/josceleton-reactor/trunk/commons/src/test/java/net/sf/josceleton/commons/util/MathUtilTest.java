package net.sf.josceleton.commons.util;

import static net.sf.josceleton.commons.test.matcher.JosceletonMatchers.hasSinglePrivateNullifiedConstructorAndInvokeIt;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

@SuppressWarnings("boxing")
public class MathUtilTest {

	@Test public final void hasUtilityConstructor() {
		assertThat(CloseableUtil.class, hasSinglePrivateNullifiedConstructorAndInvokeIt());
	}
	@DataProvider(name = "relativeToDp")
	public final Object[][] relativeToDp() {
		return new Object[][] {
			new Object[] { -50, 0 },
			new Object[] { 0, 0 },
			new Object[] { 44, 10 },
			new Object[] { 60, 50 },
			new Object[] { 80, 100 },
			new Object[] { 300, 100 }
		};
	}
	
	@Test(dataProvider = "relativeToRealReversedDp")
	public final void relativateToRealReversed(final int realValue, final int expectedValue) {
		assertThat(MathUtil.relativateTo(80, 40, realValue, 0, 100), equalTo(expectedValue));
	}

	@DataProvider(name = "relativeToRealReversedDp")
	public final Object[][] relativeToRealReversedDp() {
		return new Object[][] {
			new Object[] { 100, 0 },
			new Object[] { 80, 0 },
			new Object[] { 76, 10 },
			new Object[] { 60, 50 },
			new Object[] { 44, 90 },
			new Object[] { 40, 100 },
			new Object[] { 20, 100 }
		};
	}
	
	@Test(dataProvider = "relativeToExpectedReversedDp")
	public final void relativateToExpectedReversed(final int realValue, final int expectedValue) {
		assertThat(MathUtil.relativateTo(40, 80, realValue, 100, 0), equalTo(expectedValue));
	}
	@DataProvider(name = "relativeToExpectedReversedDp")
	public final Object[][] relativeToExpectedReversedDp() {
		return new Object[][] {
				new Object[] { -20, 100 },
				new Object[] { 0, 100 },
				new Object[] { 20, 100 },
				new Object[] { 40, 100 },
				new Object[] { 44, 90 },
				new Object[] { 60, 50 },
				new Object[] { 76, 10 },
				new Object[] { 80, 0 },
				new Object[] { 300, 0 }
		};
	}
	
	@Test(dataProvider = "relativeToBothReversedDp")
	public final void relativateToBothReversed(final int realValue, final int expectedValue) {
		assertThat(MathUtil.relativateTo(80, 40, realValue, 100, 0), equalTo(expectedValue));
	}
	@DataProvider(name = "relativeToBothReversedDp")
	public final Object[][] relativeToBothReversedDp() {
		return new Object[][] {
				new Object[] { 100, 100 },
				new Object[] { 80, 100 },
				new Object[] { 76, 90 },
				new Object[] { 60, 50 },
				new Object[] { 44, 10 },
				new Object[] { 40, 0 },
				new Object[] { 20, 0 },
				new Object[] { 0, 0 },
				new Object[] { -20, 0 }
		};
	}
}
