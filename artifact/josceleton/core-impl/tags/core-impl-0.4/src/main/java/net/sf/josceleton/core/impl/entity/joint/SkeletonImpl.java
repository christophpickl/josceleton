package net.sf.josceleton.core.impl.entity.joint;

import java.util.HashMap;
import java.util.Map;

import net.sf.josceleton.core.api.entity.Coordinate;
import net.sf.josceleton.core.api.entity.joint.Joint;
import net.sf.josceleton.core.api.entity.joint.SkeletonCoordinateUnavailableException;

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
	
}
