package net.sf.josceleton.prototype.console.util;

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
	@Deprecated // unused
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
	@Deprecated @SuppressWarnings("unused")
	public static int relativateTo(final StartEnd real, final int realValue, final StartEnd expected) {
		return 0; // FIXME MathUtil in console-prototype => use RangeScaler instead
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
