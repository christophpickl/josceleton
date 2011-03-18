package net.sf.josceleton.core.api.entity;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;


/**
 * @since 0.4
 */
public final class CoordinateUtil {

	private static final float LOWER_LIMIT_XYZ = 0.0F;
	
	private static final Map<XyzDirection, Float> UPPER_LIMITS;
	static {
		final Map<XyzDirection, Float> tmp = new HashMap<XyzDirection, Float>();
		final Float upperLimitXy = Float.valueOf(1.0F);
		final Float upperLimitZ = Float.valueOf(7.0F);
		tmp.put(XyzDirection.X, upperLimitXy);
		tmp.put(XyzDirection.Y, upperLimitXy);
		tmp.put(XyzDirection.Z, upperLimitZ);
		UPPER_LIMITS = Collections.unmodifiableMap(tmp);
	}
	
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
	
	public static boolean isCorrectValue(final float value, final XyzDirection direction) {
		return value >= LOWER_LIMIT_XYZ && value <= UPPER_LIMITS.get(direction).floatValue();
	}
	
	public static String getCorrectValueLabel(final XyzDirection direction) {
		return "[" + LOWER_LIMIT_XYZ + "-" + UPPER_LIMITS.get(direction) + "]";
	}
	
}
