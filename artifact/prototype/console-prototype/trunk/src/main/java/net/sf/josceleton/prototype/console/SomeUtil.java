package net.sf.josceleton.prototype.console;

import net.sf.josceleton.core.api.entity.Coordinate;
import net.sf.josceleton.core.api.entity.Direction;

public class SomeUtil {

	public static int prettyPrint(final Coordinate coordinate, final Direction direction) {
		final float rawValue;
		
		if(direction == Direction.X) {
			rawValue = coordinate.x();
		} else if(direction == Direction.Y) {
			rawValue = coordinate.y();
		} else { // there should be no 4th dimension in the near future ;)
			rawValue = coordinate.z();
		}
		
		return Math.round(rawValue * 100);
	}
	// TEST:
//	@DataProvider(name = "providePrettyCoordinateValues")
//	public final Object[][] providePrettyCoordinateValues() {
//		return new Object[][] {
//				new Object[] { -0.1F, -10 },
//				new Object[] {  0.1F,  10 },
//				new Object[] {  0.5F,  50 },
//				new Object[] {  1.1F, 110 },
//				new Object[] {  0.1768F, 18 }
//		};
//	}
//	
//	@Test(dataProvider = "providePrettyCoordinateValues")
//	public final void testPrettyPrint(final float xyz, final int expected) {
//		final Coordinate coordinate = TestableCoordinate.newWithXyz(xyz, xyz, xyz);
//		for (final Direction direction : Direction.values()) {
//			assertThat(CoordinateUtil.prettyPrint(coordinate, direction), equalTo(expected));
//		}
//	}
}
