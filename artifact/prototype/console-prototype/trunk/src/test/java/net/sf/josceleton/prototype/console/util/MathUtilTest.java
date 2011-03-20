package net.sf.josceleton.prototype.console.util;

import static net.sf.josceleton.commons.test.matcher.JosceletonMatchers.hasSinglePrivateNullifiedConstructorAndInvokeIt;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

@SuppressWarnings("boxing")
public class MathUtilTest {

	@Test public final void hasUtilityConstructor() {
		assertThat(MathUtil.class, hasSinglePrivateNullifiedConstructorAndInvokeIt());
	}
	
	@Test public final void checkForMinOrMax() {
		assertThat(MathUtil.checkForMinOrMax(0, 30, true), equalTo(0));
		assertThat(MathUtil.checkForMinOrMax(50, 30, true), equalTo(30));
		assertThat(MathUtil.checkForMinOrMax(0, 30, false), equalTo(30));
		assertThat(MathUtil.checkForMinOrMax(50, 30, false), equalTo(50));
	}
	@Test(dataProvider = "provideComputeCubicEaseOut")
	public final void testSomputeCubicEaseOut(final float percent, final int startValue, final int endValue,
			final int expectedValue) {
		assertThat(MathUtil.computeCubicEaseOut(percent, startValue, endValue), equalTo(expectedValue));
	}
	@DataProvider(name = "provideComputeCubicEaseOut")
	public final Object[][] provideComputeCubicEaseOut() {
		return new Object[][] {
				new Object[] { 0.0F, 0, 100,   0 },
				new Object[] { 0.1F, 0, 100,  27 },
				new Object[] { 0.2F, 0, 100,  48 },
				new Object[] { 0.5F, 0, 100,  87 },
				new Object[] { 0.8F, 0, 100,  99 },
				new Object[] { 1.0F, 0, 100, 100 },
				// reversed
				new Object[] { 0.0F, 100, 0, 100 },
				new Object[] { 0.1F, 100, 0,  73 },
				new Object[] { 0.2F, 100, 0,  52 },
				new Object[] { 0.8F, 100, 0,   1 },
				new Object[] { 1.0F, 100, 0,   0 }
		};
	}
	
}
