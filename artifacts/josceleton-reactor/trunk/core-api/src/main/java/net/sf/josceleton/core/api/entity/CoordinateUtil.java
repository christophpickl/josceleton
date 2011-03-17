package net.sf.josceleton.core.api.entity;

/**
 * @since 0.4
 */
public final class CoordinateUtil {
	
	private CoordinateUtil() {
		// utility class is not instantiable
	}
	
	/**
	 * @since 0.4
	 */
	public static int prettyPrint(final Coordinate coordinate, final XyzDirection direction) {
		final float rawValue;
		
		if(direction == XyzDirection.X) {
			rawValue = coordinate.x();
		} else if(direction == XyzDirection.Y) {
			rawValue = coordinate.y();
		} else { // there should be no 4th dimension in the near future ;)
			rawValue = coordinate.z();
		}
		
		return Math.round(rawValue * 100);
	}
	
}
