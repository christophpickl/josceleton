package net.sf.josceleton.commons.util;

/**
 * @since 0.4
 */
public final class MathUtil {
	
	private MathUtil() {
        // utility class should not be instantiable
	}


	/**
	 * @since 0.4
	 */
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
	public static int relativateTo(final StartEnd real, final int realValue,
                final StartEnd expected) {

        final int diffExpected = Math.abs(expected.start() - expected.end());
        final double diffRealPercentExpectedAdjusted = MathUtil.calcDiffRealPercentExpectedAdjusted(
        		real, realValue, expected.start() < expected.end());
        
        return (int) Math.round(diffExpected * diffRealPercentExpectedAdjusted);
	}

	private static double calcDiffRealPercentExpectedAdjusted(final StartEnd real,
                final int realValue, final boolean expectedStartLower) {
        final double diffRealPercent = MathUtil.calcDiffRealPercent(real, realValue);
        final double diffRealPercentExpectedAdjusted;
        
        if(expectedStartLower) {
                diffRealPercentExpectedAdjusted = diffRealPercent;
        } else {
                diffRealPercentExpectedAdjusted = 1 - diffRealPercent;
        }
        
        return diffRealPercentExpectedAdjusted;
	}

	private static double calcDiffRealPercent(final StartEnd real, final int realValue) {
        final int diffReal = Math.abs(real.start() - real.end());
        final int realAdjustedValue = MathUtil.calcRealAdjustedValue(real, realValue);
        
        final int realMin = Math.min(real.start(), real.end());
        
        final double diffRealPercent;
        if(real.start() < real.end()) {
                diffRealPercent = (realAdjustedValue - realMin) / ((double) diffReal);
        } else {
                diffRealPercent = 1.0d - (realAdjustedValue - realMin) / ((double) diffReal);
        }
        
        return diffRealPercent;
	}

	private static int calcRealAdjustedValue(final StartEnd real, final int realValue) {
        final int realAdjustedValue;
        final int realMin = Math.min(real.start(), real.end());
        final int realMax = Math.max(real.start(), real.end());
        
        if(realValue < realMin) {
                realAdjustedValue = realMin;
        } else if(realValue > realMax) {
                realAdjustedValue = realMax;
        } else {
                realAdjustedValue = realValue;
        }
        return realAdjustedValue;
	}
	
	/**
	 * @since 0.4
	 */
	public static class StartEnd {
		
		private final int start;
		private final int end;
		
		public StartEnd(final int start, final int end) {
			this.start = start;
			this.end = end;
		}
		
		final int start() {
			return this.start;
		}
		
		final int end() {
			return this.end;
		}
	}

}
