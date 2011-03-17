package net.sf.josceleton.commons.util;

/**
 * @since 0.4
 */
public final class MathUtil {
	
	// TODO duplicate class of pulseproject/commons
	
	private MathUtil() {
        // utility class should not be instantiable
	}

	
	public static int checkForMinOrMax(final int limit, final int value, final boolean forMin) {
		if(forMin == true) {
			return Math.min(limit, value);
		}
		return Math.max(limit, value);
	}
	
	/**
	 * @since 0.4
	 */
	public static int computeCubicEaseOut(final float percent, final int startValue, final int endValue) {
        final int diff = Math.abs(endValue - startValue);
        
        // thanks to: http://www.dishevelled.org/interpolate/
//      final double expAdjustedPercent = -1.0d * percent * (percent - 2.0d); // ease out quadratic
        final double expAdjustedPercent = Math.pow(percent - 1.0d, 3.0d) + 1.0d; // ease out cubic
        
        int diffToChange = (int) (diff * expAdjustedPercent);
        if(startValue > endValue) {
                diffToChange *= -1;
        }
        
        return startValue + diffToChange;
	}

	/**
	 * @since 0.4
	 */
	public static int relativateTo(final int realStart, final int realEnd, final int realValue,
                final int expectedStart, final int expectedEnd) {

        final int diffExpected = Math.abs(expectedStart - expectedEnd);
        final double diffRealPercentExpectedAdjusted = MathUtil.calcDiffRealPercentExpectedAdjusted(
                        realStart, realEnd, realValue, expectedStart < expectedEnd);
        
        return (int) Math.round(diffExpected * diffRealPercentExpectedAdjusted);
	}

	private static double calcDiffRealPercentExpectedAdjusted(final int realStart, final int realEnd,
                final int realValue, final boolean expectedStartLower) {
        
        final double diffRealPercent = MathUtil.calcDiffRealPercent(realStart, realEnd, realValue);
        final double diffRealPercentExpectedAdjusted;
        
        if(expectedStartLower) {
                diffRealPercentExpectedAdjusted = diffRealPercent;
        } else {
                diffRealPercentExpectedAdjusted = 1 - diffRealPercent;
        }
        
        return diffRealPercentExpectedAdjusted;
	}

	private static double calcDiffRealPercent(final int realStart, final int realEnd, final int realValue) {
        final int diffReal = Math.abs(realStart - realEnd);
        final int realAdjustedValue = MathUtil.calcRealAdjustedValue(realStart, realEnd, realValue);
        
        final int realMin = Math.min(realStart, realEnd);
        
        final double diffRealPercent;
        if(realStart < realEnd) {
                diffRealPercent = (realAdjustedValue - realMin) / ((double) diffReal);
        } else {
                diffRealPercent = 1.0d - (realAdjustedValue - realMin) / ((double) diffReal);
        }
        
        return diffRealPercent;
	}

	private static int calcRealAdjustedValue(final int realStart, final int realEnd, final int realValue) {
        final int realAdjustedValue;
        final int realMin = Math.min(realStart, realEnd);
        final int realMax = Math.max(realStart, realEnd);
        
        if(realValue < realMin) {
                realAdjustedValue = realMin;
        } else if(realValue > realMax) {
                realAdjustedValue = realMax;
        } else {
                realAdjustedValue = realValue;
        }
        return realAdjustedValue;
	}
	
}
