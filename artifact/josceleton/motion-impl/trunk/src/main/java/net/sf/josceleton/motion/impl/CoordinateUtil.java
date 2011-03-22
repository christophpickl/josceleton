package net.sf.josceleton.motion.impl;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import net.sf.josceleton.core.api.entity.location.Direction;

/**
 * @since 0.4
 */
@Deprecated // TODO completely delete this class, as we dont have (may have!) any assumptions about coordinate values
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
	 * @return e.g.: "[0.0-1.0]" for X and Y, "[0.0-7.0]" for Z
	 * @since 0.4
	 */
	public static String getCorrectValueLabel(final Direction direction) {
		return "[" + LOWER_LIMIT_XYZ + "-" + UPPER_LIMITS.get(direction) + "]";
	}

	public static float getLowerLimit(/*not necessary ;) final Direction direction*/) {
		return LOWER_LIMIT_XYZ;
	}

	public static float getUpperLimit(final Direction direction) {
		return UPPER_LIMITS.get(direction).floatValue();
	}
	
}
