package net.sf.josceleton.commons.util;

import java.util.Random;

/**
 * @since 0.4
 */
public final class RandomUtil {

	// TODO duplicate class of pulseproject/commons
	
	private static final Random R = new Random();

	private RandomUtil() {
		// utility class is not instantiable
	}

	/**
	 * @since 0.4
	 */
	public static int randFromZeroToExclusive(final int exclusiveMax) {
        return R.nextInt(exclusiveMax);
	}

	/**
	 * @since 0.4
	 */
	public static int generateWithinRange(final int middle, final int deviation) {
        final int start = middle - deviation;
        final int randMax = deviation * 2 + 1;
        final int randValue = RandomUtil.R.nextInt(randMax);
        return start + randValue;
	}

}
