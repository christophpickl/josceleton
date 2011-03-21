package net.sf.josceleton.playground.motion.app2.framework.world;

import java.awt.Point;

import net.sf.josceleton.core.api.entity.joint.Joint;
import net.sf.josceleton.core.api.entity.joint.Skeleton;
import net.sf.josceleton.core.api.entity.location.Coordinate;
import net.sf.josceleton.core.api.entity.location.Range;
import net.sf.josceleton.core.api.entity.location.RangeScaler;
import net.sf.josceleton.playground.motion.common.DrawSurface;

public class WorldSnapshotFactory {
	
	private final RangeScaler scaler;
	private final Range rangeX;
	private final Range rangeY;
	private final int gap;
	private final Joint cursorJoint;
	private final DrawSurface surface;
	
	public WorldSnapshotFactory(Joint cursorJoint, RangeScaler scaler, Range rangeX, Range rangeY, int gap, DrawSurface surface) {
		this.cursorJoint = cursorJoint;
		this.scaler = scaler;
		this.rangeX = rangeX;
		this.rangeY = rangeY;
		this.gap = gap;
		this.surface = surface;
	}

	public WorldSnapshot create(Skeleton skeleton) {
		if(skeleton == null) {
			return null;
		}
		if(skeleton.isCoordinateAvailable(this.cursorJoint) == false) {
			System.err.println("WorldSnapshotFactory: No joint data available for handcursor; returning NULL");
			return null;
		}
		
		final Coordinate coordinate = skeleton.getNullSafe(this.cursorJoint);
		final Point locationRHand = new Point(this.scaler.scale(coordinate.x(), this.rangeX) + this.gap,
						 this.scaler.scale(coordinate.y(), this.rangeY) + this.gap);
		return new WorldSnapshot(locationRHand, skeleton, this.surface);
	}

	public WorldSnapshot createInitialDummy() {
		System.out.println("WorldSnapshotFactory: createInitialDummy()");
		return new WorldSnapshot(new Point(), null, this.surface);
	}
}
