package net.sf.josceleton.core.impl.entity.body;

import java.util.HashMap;
import java.util.Map;

import net.sf.josceleton.core.api.entity.Coordinate;
import net.sf.josceleton.core.api.entity.body.BodyPart;
import net.sf.josceleton.core.api.entity.body.SkeletonCoordinateUnavailableException;

/**
 * @since 0.4
 */
class SkeletonImpl implements SkeletonInternal {
	
	private final Map<BodyPart, Coordinate> coordinateByBodyPart = new HashMap<BodyPart, Coordinate>();

	/** {@inheritDoc} from {@link Skeleton} */
	@Override public final Coordinate get(final BodyPart part) {
		return this.coordinateByBodyPart.get(part);
	}

	/** {@inheritDoc} from {@link Skeleton} */
	@Override public final Coordinate getNullSafe(final BodyPart part) {
		final Coordinate storedCoordinate = this.get(part);
		if(storedCoordinate == null) {
			throw SkeletonCoordinateUnavailableException.newUnavailable(part);
		}
		return storedCoordinate;
	}

	/** {@inheritDoc} from {@link Skeleton} */
	@Override public final boolean isCoordinateAvailable(final BodyPart part) {
		return this.coordinateByBodyPart.containsKey(part);
	}

	/** {@inheritDoc} from {@link SkeletonInternal} */
	@Override public final void update(final BodyPart bodyPart, final Coordinate coordinate) {
		this.coordinateByBodyPart.put(bodyPart, coordinate);
	}
	
	// TODO implement equals/hashCode/toString for SkeletonImpl
}
