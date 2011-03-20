package net.sf.josceleton.core.impl.entity.location;

import net.sf.josceleton.core.api.entity.location.Range;
import net.sf.josceleton.core.api.entity.location.RangeScaler;

/**
 * @since 0.5
 */
class RangeScalerImpl implements RangeScaler {
	
	/** {@inheritDoc} from {@link RangeScaler} */
	@Override public final int scale(final float coordinate, final Range range) {
		final float f1 = range.getFromStart();
		final float f2 = range.getFromEnd();
		final int t1 = range.getToStart();
		final int t2 = range.getToEnd();
		
        final int diffExpected = Math.abs(t1 - t2);
        final double diffRealPercentExpectedAdjusted =
        	RangeScalerImpl.calcDiffRealPercentExpectedAdjusted(f1, f2, coordinate, t1 < t2);
        
        return (int) Math.round(diffExpected * diffRealPercentExpectedAdjusted);
	}

	private static double calcDiffRealPercentExpectedAdjusted(final float f1, final float f2,
                final float coordinate, final boolean expectedStartLower) {
        final double diffRealPercent = RangeScalerImpl.calcDiffRealPercent(f1, f2, coordinate);
        final double diffRealPercentExpectedAdjusted;
        
        if(expectedStartLower) {
                diffRealPercentExpectedAdjusted = diffRealPercent;
        } else {
                diffRealPercentExpectedAdjusted = 1 - diffRealPercent;
        }
        
        return diffRealPercentExpectedAdjusted;
	}

	private static double calcDiffRealPercent(final float f1, final float f2, final float coordinate) {
        final float diffReal = Math.abs(f1 - f2);
        final float realAdjustedValue = RangeScalerImpl.calcRealAdjustedValue(f1, f2, coordinate);
        
        final float realMin = Math.min(f1, f2);
        
        final double diffRealPercent;
        if(f1 < f2) {
                diffRealPercent = (realAdjustedValue - realMin) / ((double) diffReal);
        } else {
                diffRealPercent = 1.0d - (realAdjustedValue - realMin) / ((double) diffReal);
        }
        
        return diffRealPercent;
	}

	private static float calcRealAdjustedValue(final float f1, final float f2, final float coordinate) {
        final float realAdjustedValue;
        final float realMin = Math.min(f1, f2);
        final float realMax = Math.max(f1, f2);
        
        if(coordinate < realMin) {
                realAdjustedValue = realMin;
        } else if(coordinate > realMax) {
                realAdjustedValue = realMax;
        } else {
                realAdjustedValue = coordinate;
        }
        return realAdjustedValue;
	}

}
