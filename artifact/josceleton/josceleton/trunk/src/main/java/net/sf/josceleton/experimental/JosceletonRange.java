package net.sf.josceleton.experimental;

import net.sf.josceleton.Josceleton;
import net.sf.josceleton.core.api.entity.location.Range;
import net.sf.josceleton.core.api.entity.location.RangeFactory;

public class JosceletonRange implements Range {
	
	private static final RangeFactory FACTORY = Josceleton.getRangeFactory();
	
	private final Range delegate;
	
	public JosceletonRange(
			final float fromStart,
			final float fromEnd,
			final int toStart,
			final int toEnd) {
		this.delegate = FACTORY.createSpecific(fromStart, fromEnd, toStart, toEnd);
	}

	@Override public final float getFromStart() {
		return this.delegate.getFromEnd();
	}
	
	@Override public final float getFromEnd() {
		return this.delegate.getFromEnd();
	}

	@Override public final int getToStart() {
		return this.delegate.getToStart();
	}

	@Override public final int getToEnd() {
		return this.delegate.getToEnd();
	}

}
