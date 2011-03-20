package net.sf.josceleton.core.impl.entity.location;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import net.sf.josceleton.core.api.entity.location.Range;
import net.sf.josceleton.core.api.entity.location.RangeScaler;
import net.sf.josceleton.core.api.test.TestableRange;

/**
 * @since 0.5
 */
class RangeScalerImpl implements RangeScaler {
	
	public static void main(String[] args) {
//		System.out.println("80 = " + new RangeScalerImpl().scale(0.2F, new TestableRangeX(0.9F, 0.6F, 50, 80)));
		System.out.println("80 = " + new RangeScalerImpl().scale(0.2F, new TestableRangeX(0.6F, 0.9F, 80, 50)));
	}
	private static class TestableRangeX implements Range {
		private final float fromStart;
		private final float fromEnd;
		private final int toStart;
		private final int toEnd;
		public TestableRangeX(final float fromStart, final float fromEnd, final int toStart, final int toEnd) {
			this.fromStart = fromStart;
			this.fromEnd = fromEnd;
			this.toStart = toStart;
			this.toEnd = toEnd;
		}
		public final float getFromStart() {
			return this.fromStart;
		}
		public final float getFromEnd() {
			return this.fromEnd;
		}
		public final int getToStart() {
			return this.toStart;
		}
		public final int getToEnd() {
			return this.toEnd;
		}
	}

	/** {@inheritDoc} from {@link RangeScaler} */
	@Override public final int scale(final float coordinate, final Range range) {
		final float f1 = range.getFromStart();
		final float f2 = range.getFromEnd();
		final int t1 = range.getToStart();
		final int t2 = range.getToEnd();
		
        final int diffExpected = Math.abs(t1 - t2);
        final double diffRealPercentExpectedAdjusted =
        	RangeScalerImpl.calcDiffFromPercentExpectedAdjusted(f1, f2, coordinate, t1 < t2);
        
        final int addOn;
        if(f1 > f2 && t1 > t2) {
        	addOn = 0;
		} else if(f1 > f2) {
        	addOn = t1;
        } else if(t1 > t2) {
        	addOn = t2;
        } else {
        	addOn = 0;
        }
        return (int) Math.round(addOn + diffExpected * diffRealPercentExpectedAdjusted);
	}

	private static double calcDiffFromPercentExpectedAdjusted(final float f1, final float f2,
                final float coordinate, final boolean expectedStartLower) {
        final double diffFromPercent = RangeScalerImpl.calcDiffFromPercent(f1, f2, coordinate);
        final double diffFromPercentExpectedAdjusted;
        
        if(expectedStartLower) {
                diffFromPercentExpectedAdjusted = diffFromPercent;
        } else {
                diffFromPercentExpectedAdjusted = 1 - diffFromPercent;
        }
        
        return diffFromPercentExpectedAdjusted;
	}

	private static double calcDiffFromPercent(final float f1, final float f2, final float coordinate) {
        final float diffFrom = Math.abs(f1 - f2);
        final float fromAdjustedValue = RangeScalerImpl.calcFromAdjustedValue(f1, f2, coordinate);
        
        final float fromMin = Math.min(f1, f2);
        
        final double diffFromPercent;
        if(f1 < f2) {
                diffFromPercent = (fromAdjustedValue - fromMin) / ((double) diffFrom);
        } else {
                diffFromPercent = 1.0d - (fromAdjustedValue - fromMin) / ((double) diffFrom);
        }
        
        return diffFromPercent;
	}

	private static float calcFromAdjustedValue(final float f1, final float f2, final float coordinate) {
        final float fromAdjustedValue;
        final float fromMin = Math.min(f1, f2);
        final float fromMax = Math.max(f1, f2);
        
        if(coordinate < fromMin) {
                fromAdjustedValue = fromMin;
        } else if(coordinate > fromMax) {
                fromAdjustedValue = fromMax;
        } else {
                fromAdjustedValue = coordinate;
        }
        return fromAdjustedValue;
	}

}
