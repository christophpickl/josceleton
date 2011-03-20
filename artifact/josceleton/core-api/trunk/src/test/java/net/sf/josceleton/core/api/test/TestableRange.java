package net.sf.josceleton.core.api.test;

import net.sf.josceleton.core.api.entity.location.Range;

public class TestableRange implements Range {
	
	private final float fromStart;
	
	private final float fromEnd;
	
	private final int toStart;
	
	private final int toEnd;
	
	
	public TestableRange(final float fromStart, final float fromEnd, final int toStart, final int toEnd) {
		this.fromStart = fromStart;
		this.fromEnd = fromEnd;
		this.toStart = toStart;
		this.toEnd = toEnd;
	}

	/** {@inheritDoc} from {@link Range} */
	public final float getFromStart() {
		return this.fromStart;
	}

	/** {@inheritDoc} from {@link Range} */
	public final float getFromEnd() {
		return this.fromEnd;
	}

	/** {@inheritDoc} from {@link Range} */
	public final int getToStart() {
		return this.toStart;
	}

	/** {@inheritDoc} from {@link Range} */
	public final int getToEnd() {
		return this.toEnd;
	}
	
	@Override public final String toString() {
		return "TestableRange[" +
				 "from: " + this.fromStart + "/" + this.fromEnd + ", " +
				 "to: "   + this.toStart   + "/" + this.toEnd +
				"]";
	}

}
