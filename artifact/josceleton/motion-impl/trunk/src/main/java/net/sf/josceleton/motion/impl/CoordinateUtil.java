package net.sf.josceleton.motion.impl;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import net.sf.josceleton.core.api.entity.Direction;

/**
 * @since 0.4
 */
public final class CoordinateUtil {

	private static final float LOWER_LIMIT_XYZ = 0.0F;
	
	private static final Map<Direction, Float> UPPER_LIMITS;
	static {
		final Map<Direction, Float> tmp = new HashMap<Direction, Float>();
		final Float upperLimitXy = Float.valueOf(1.0F);
		final Float upperLimitZ = Float.valueOf(7.0F);
		tmp.put(Direction.X, upperLimitXy);
		tmp.put(Direction.Y, upperLimitXy);
		tmp.put(Direction.Z, upperLimitZ);
		UPPER_LIMITS = Collections.unmodifiableMap(tmp);
	}
	
	private CoordinateUtil() {
		// utility class is not instantiable
	}
	
	/**
	 * @since 0.4
	 */
	public static boolean isCorrectValue(final float value, final Direction direction) {
		return value >= LOWER_LIMIT_XYZ && value <= UPPER_LIMITS.get(direction).floatValue();
	}

	/**
	 * @since 0.4
	 */
	public static String getCorrectValueLabel(final Direction direction) {
		return "[" + LOWER_LIMIT_XYZ + "-" + UPPER_LIMITS.get(direction) + "]";
	}
	
}
