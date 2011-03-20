package net.sf.josceleton.core.api.entity.location;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import net.sf.josceleton.core.api.test.TestableRange;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

/**
 * @since 0.5
 */
@SuppressWarnings("boxing")
public abstract class RangeScalerTest {
	
	protected abstract RangeScaler newTestee();
	
	private RangeScaler testee;
	
	@BeforeMethod public final void setUpTestee() {
		this.testee = this.newTestee();
	}
	@AfterMethod public final void tearDownTestee() {
		this.testee = null;
	}
	

	@Test(dataProvider = "provideScaleMostCommonCase")
	public final void scaleMostCommonCase(final float coordinate, final int expectedResult) {
		assertScale(coordinate, 0.4F, 0.8F, 0, 100, expectedResult);
	}
	@DataProvider(name = "provideScaleMostCommonCase")
	public final Object[][] provideScaleMostCommonCase() {
		return new Object[][] {
			new Object[] { -0.50F,   0 },
			new Object[] {  0.00F,   0 },
			new Object[] {  0.39F,   0 },
			new Object[] {  0.44F,  10 },
			new Object[] {  0.60F,  50 },
			new Object[] {  0.80F, 100 },
			new Object[] {  3.00F, 100 }
		};
	}
	
	@Test(dataProvider = "provideScaleReversed")
	public final void scaleReversed(final float coordinate, final int expectedResult) {
		assertScale(coordinate, 0.8F, 0.4F, 0, 100, expectedResult);
	}
	@DataProvider(name = "provideScaleReversed")
	public final Object[][] provideScaleReversed() {
		return new Object[][] {
			new Object[] { 1.00F,   0 },
			new Object[] { 0.80F,   0 },
			new Object[] { 0.76F,  10 },
			new Object[] { 0.60F,  50 },
			new Object[] { 0.44F,  90 },
			new Object[] { 0.40F, 100 },
			new Object[] { 0.20F, 100 }
		};
	}
	
	@Test(dataProvider = "provideScaleResultReversed")
	public final void scaleResultReversed(final float coordinate, final int expectedResult) {
		assertScale(coordinate, 0.4F, 0.8F, 100, 0, expectedResult);
	}
	@DataProvider(name = "provideScaleResultReversed")
	public final Object[][] provideScaleResultReversed() {
		return new Object[][] {
				new Object[] { -0.20F, 100 },
				new Object[] {  0.00F, 100 },
				new Object[] {  0.20F, 100 },
				new Object[] {  0.40F, 100 },
				new Object[] {  0.44F,  90 },
				new Object[] {  0.60F,  50 },
				new Object[] {  0.76F,  10 },
				new Object[] {  0.80F,   0 },
				new Object[] {  3.00F,   0 }
		};
	}
	
	@Test(dataProvider = "provideScaleBothReversed")
	public final void scaleBothReversed(final float coordinate, final int expectedResult) {
		assertScale(coordinate, 0.8F, 0.4F, 100, 0, expectedResult);
	}
	@DataProvider(name = "provideScaleBothReversed")
	public final Object[][] provideScaleBothReversed() {
		return new Object[][] {
				new Object[] {  1.00F, 100 },
				new Object[] {  0.80F, 100 },
				new Object[] {  0.76F,  90 },
				new Object[] {  0.60F,  50 },
				new Object[] {  0.44F,  10 },
				new Object[] {  0.40F,   0 },
				new Object[] {  0.20F,   0 },
				new Object[] {  0.00F,   0 },
				new Object[] { -0.20F,   0 }
		};
	}

	@Test public final void wasOnceABug() {
		assertThat(newTestee().scale(0.20667548F, new TestableRange(0.9F, 0.5F, 50, 80)), equalTo(80));
		assertThat(newTestee().scale(0.20667548F, new TestableRange(0.5F, 0.9F, 80, 50)), equalTo(80));
	}
	
	private void assertScale(
			final float coordinate,
			final float f1, final float f2,
			final int t1, final int t2,
			final int expectedValue) {
		assertThat(this.testee.scale(coordinate, new TestableRange(f1, f2, t1, t2)), equalTo(expectedValue));
	}
	
}
