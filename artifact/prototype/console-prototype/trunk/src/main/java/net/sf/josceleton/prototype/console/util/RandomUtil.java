package net.sf.josceleton.prototype.console.util;

import java.util.Random;

/**
 * @since 0.4
 */
public final class RandomUtil {

	private static final Random R = new Random();

	private RandomUtil() {
		// utility class is not instantiable
	}

	/**
	 * @since 0.4
	 */
	public static int nextIntZeroToExclusive(final int exclusiveMax) {
        return R.nextInt(exclusiveMax);
	}

	
	/**
	 * A float between 0.0 (inclusive) and 1.0 (exclusive).
	 * 
	 * @since 0.5
	 */
	public static float nextFloat() {
        return R.nextFloat();
	}
	
	/**
	 * @since 0.5
	 */
	public static int nextIntZeroToInclusive(final int inclusiveMax) {
        return R.nextInt(inclusiveMax + 1);
	}

	/**
	 * Generates a number within the given range.
	 * 
	 * Example: middle = 50; deviation = 10; result will be = [40..60]
	 * 
	 * @param middle fixed point
	 * @param deviation the number can be different from middle point
	 * @return  a number max <code>deviation</code> away from <code>middle</middle>
	 * @since 0.4
	 */
	public static int nextIntWithinRange(final int middle, final int deviation) {
        final int start = middle - deviation;
        final int randMax = deviation * 2 + 1;
        final int randValue = RandomUtil.R.nextInt(randMax);
        return start + randValue;
	}

}
