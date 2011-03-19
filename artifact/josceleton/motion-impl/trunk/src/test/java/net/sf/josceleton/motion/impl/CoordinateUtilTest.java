package net.sf.josceleton.motion.impl;

import static net.sf.josceleton.commons.test.matcher.JosceletonMatchers.hasSinglePrivateNullifiedConstructorAndInvokeIt;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import net.sf.josceleton.core.api.entity.location.Coordinate;
import net.sf.josceleton.core.api.entity.location.Direction;
import net.sf.josceleton.core.api.test.TestableCoordinate;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

@SuppressWarnings("boxing")
public class CoordinateUtilTest {
	
	@Test
	public final void hasUtilityConstructor() {
		assertThat(CoordinateUtil.class, hasSinglePrivateNullifiedConstructorAndInvokeIt());
	}
	
	
	@Test(dataProvider = "provideCorrectValuesOrNot")
	public final void isCorrectValue(final float coordinateValue,
			final Direction direction, final boolean expectedCorrect) {
		assertThat(CoordinateUtil.isCorrectValue(coordinateValue, direction), equalTo(expectedCorrect));
	}
	@DataProvider(name = "provideCorrectValuesOrNot")
	public final Object[][] provideCorrectValuesOrNot() {
		return new Object[][] {
			new Object[] {  0.0F, Direction.X, true },
			new Object[] {  0.5F, Direction.X, true },
			new Object[] {  1.0F, Direction.X, true },
			new Object[] {  0.0F, Direction.Z, true },
			new Object[] {  1.0F, Direction.Z, true },
			new Object[] {  7.0F, Direction.Z, true },
			
			new Object[] {  7.0F, Direction.Y, false},
			new Object[] { -1.0F, Direction.Y, false},
			new Object[] {  8.0F, Direction.Z, false},
			new Object[] {  7.1F, Direction.Z, false},
			new Object[] { -1.0F, Direction.Z, false}
		};
	}
	
	@Test
	public final void getCorrectValueLabel() {
		assertThat(CoordinateUtil.getCorrectValueLabel(Direction.X), equalTo("[0.0-1.0]"));
		assertThat(CoordinateUtil.getCorrectValueLabel(Direction.Y), equalTo("[0.0-1.0]"));
		assertThat(CoordinateUtil.getCorrectValueLabel(Direction.Z), equalTo("[0.0-7.0]"));
	}
	
}
