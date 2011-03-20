package net.sf.josceleton.core.impl.entity.joint;

import java.util.HashMap;
import java.util.Map;

import net.sf.josceleton.core.api.entity.joint.Joint;
import net.sf.josceleton.core.api.entity.joint.Joints;
import net.sf.josceleton.core.api.entity.joint.Skeleton;
import net.sf.josceleton.core.api.entity.joint.SkeletonCoordinateUnavailableException;
import net.sf.josceleton.core.api.entity.location.Coordinate;

/**
 * @since 0.4
 */
class SkeletonImpl implements SkeletonInternal {
	
	private final Map<Joint, Coordinate> coordinateByJoint = new HashMap<Joint, Coordinate>();

	/** {@inheritDoc} from {@link Skeleton} */
	@Override public final Coordinate get(final Joint joint) {
		return this.coordinateByJoint.get(joint);
	}

	/** {@inheritDoc} from {@link Skeleton} */
	@Override public final Coordinate getNullSafe(final Joint joint) {
		final Coordinate storedCoordinate = this.get(joint);
		if(storedCoordinate == null) {
			throw SkeletonCoordinateUnavailableException.newUnavailable(joint);
		}
		return storedCoordinate;
	}

	/** {@inheritDoc} from {@link Skeleton} */
	@Override public final boolean isCoordinateAvailable(final Joint joint) {
		return this.coordinateByJoint.containsKey(joint);
	}

	/** {@inheritDoc} from {@link SkeletonInternal} */
	@Override public final void update(final Joint joint, final Coordinate coordinate) {
		this.coordinateByJoint.put(joint, coordinate);
	}
	
	@Override public final boolean equals(final Object other) {
		if(this == other) { return true; }
		if((other instanceof Skeleton) == false) { return false; }
		final Skeleton that = (Skeleton) other;
		
		// check all coordinates
		for (final Joint joint : Joints.values()) {
			final Coordinate thisCoordinate = this.get(joint);
			final Coordinate thatCoordinate = that.get(joint);
			if(thisCoordinate != null && thatCoordinate == null ||
			   thisCoordinate == null && thatCoordinate != null) {
				return false;
			} else if(thisCoordinate != null && thatCoordinate != null &&
					(thisCoordinate.equals(thatCoordinate) == false)) {
				return false;
			} // else both are null
		}
		
		return true;
	}
	
	@Override public final int hashCode() {
		for (final Joint joint : Joints.values()) {
			final Coordinate coordinate = this.coordinateByJoint.get(joint);
			if(coordinate != null) {
				return coordinate.hashCode();
			}
		}
		return 0;
	}
}
