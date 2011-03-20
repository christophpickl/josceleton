package net.sf.josceleton.core.api.entity.location;

import com.google.inject.Inject;
import com.google.inject.assistedinject.Assisted;


/**
 * @since 0.5
 */
class RangeImpl implements Range {
	
	private final float fromStart;
	
	private final float fromEnd;
	
	private final int toStart;
	
	private final int toEnd;
	
	// MINOR Range should provide interpolation function as well (linear, exponential,... custom "curve-points")
	
	
	@Inject RangeImpl(
			@Assisted final float fromStart,
			@Assisted final float fromEnd,
			@Assisted final int toStart,
			@Assisted final int toEnd) {
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
	
	// TODO equals/hash/tostring
}
