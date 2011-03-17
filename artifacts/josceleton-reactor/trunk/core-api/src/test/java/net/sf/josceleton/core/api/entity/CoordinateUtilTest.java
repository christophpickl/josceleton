package net.sf.josceleton.core.api.entity;

import static net.sf.josceleton.commons.test.matcher.JosceletonMatchers.hasSinglePrivateNullifiedConstructorAndInvokeIt;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import net.sf.josceleton.core.api.test.TestableCoordinate;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

@SuppressWarnings("boxing")
public class CoordinateUtilTest {
	
	@Test
	public final void hasUtilityConstructor() {
		assertThat(CoordinateUtil.class, hasSinglePrivateNullifiedConstructorAndInvokeIt());
	}
	
	@DataProvider(name = "providePrettyCoordinateValues")
	public final Object[][] providePrettyCoordinateValues() {
		return new Object[][] {
				new Object[] { -0.1F, -10 },
				new Object[] {  0.1F,  10 },
				new Object[] {  0.5F,  50 },
				new Object[] {  1.1F, 110 },
				new Object[] {  0.1768F, 18 }
		};
	}
	
	@Test(dataProvider = "providePrettyCoordinateValues")
	public final void testname(final float xyz, final int expected) {
		final Coordinate coordinate = TestableCoordinate.newWithXyz(xyz, xyz, xyz);
		for (final XyzDirection direction : XyzDirection.values()) {
			assertThat(CoordinateUtil.prettyPrint(coordinate, direction), equalTo(expected));
		}
	}
	
}
