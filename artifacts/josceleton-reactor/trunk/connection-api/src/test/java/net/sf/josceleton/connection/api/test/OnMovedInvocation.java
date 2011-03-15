package net.sf.josceleton.connection.api.test;

import net.sf.josceleton.core.api.entity.Coordinate;
import net.sf.josceleton.core.api.entity.body.BodyPart;
import net.sf.josceleton.core.api.entity.body.Skeleton;

/**
 * Value object containing method arguments for {@link MotionListener}.
 * 
 * @since 0.4
 */
public class OnMovedInvocation {
	
	private final BodyPart movedPart;
	
	private final Coordinate updatedCoordinate;
	
	private final Skeleton skeleton;
	
	
	public OnMovedInvocation(
			final BodyPart movedPart,
			final Coordinate updatedCoordinate,
			final Skeleton skeleton) {
		this.movedPart = movedPart;
		this.updatedCoordinate = updatedCoordinate;
		this.skeleton = skeleton;
	}


	public final BodyPart getMovedPart() {
		return this.movedPart;
	}

	public final Coordinate getUpdatedCoordinate() {
		return this.updatedCoordinate;
	}

	public final Skeleton getSkeleton() {
		return this.skeleton;
	}
	
}
