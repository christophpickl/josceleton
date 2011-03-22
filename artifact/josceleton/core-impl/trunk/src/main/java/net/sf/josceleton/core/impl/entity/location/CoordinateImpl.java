package net.sf.josceleton.core.impl.entity.location;

import net.sf.josceleton.core.api.entity.location.Coordinate;

import com.google.inject.Inject;
import com.google.inject.assistedinject.Assisted;

class CoordinateImpl implements Coordinate {
	
	private final float x;
	
	private final float y;
	
	private final float z;
	
	
	@Inject CoordinateImpl(
			@Assisted("x") final float x,
			@Assisted("y") final float y,
			@Assisted("z") final float z) {
		// NO argument check! we dont have any assumptions, as user could have set osceleton to transform coordinates...
		this.x = x;
		this.y = y;
		this.z = z;
	}
	
	/** {@inheritDoc} from {@link Coordinate} */
	@Override public final float x() { return this.x; }
	
	/** {@inheritDoc} from {@link Coordinate} */
	@Override public final float y() { return this.y; }
	
	/** {@inheritDoc} from {@link Coordinate} */
	@Override public final float z() { return this.z; }
	

	@Override public final boolean equals(final Object other) {
		if(this == other) { return true; }
		if((other instanceof Coordinate) == false) {
			return false;
		}
		final Coordinate that = (Coordinate) other;
		return this.x() == that.x() && this.y() == that.y() && this.z() == that.z();
	}
	
	@Override public final int hashCode() {
		return Float.valueOf(this.x).hashCode();
	}
	
	@Override public final String toString() {
		return "CoordinateImpl[x=" + this.x + ", y=" + this.y + ", z=" + this.z + "]";
	}
}
