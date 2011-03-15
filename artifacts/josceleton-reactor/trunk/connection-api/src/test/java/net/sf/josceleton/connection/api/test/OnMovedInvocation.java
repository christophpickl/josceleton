package net.sf.josceleton.connection.api.test;

import net.sf.josceleton.core.api.entity.Coordinate;
import net.sf.josceleton.core.api.entity.body.BodyPart;
import net.sf.josceleton.core.api.entity.body.Skeleton;

public class OnMovedInvocation {
	public BodyPart movedPart;
	public Coordinate updatedCoordinate;
	public Skeleton skeleton;

	public OnMovedInvocation(BodyPart movedPart, Coordinate updatedCoordinate,
			Skeleton skeleton) {
		this.movedPart = movedPart;
		this.updatedCoordinate = updatedCoordinate;
		this.skeleton = skeleton;
	}
}