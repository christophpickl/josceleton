package net.sf.josceleton.playground.motion.app2.framework.world;

import java.awt.Point;

import net.sf.josceleton.core.api.entity.joint.Joint;
import net.sf.josceleton.core.api.entity.joint.Skeleton;
import net.sf.josceleton.core.api.entity.location.Coordinate;
import net.sf.josceleton.core.api.entity.location.Range;
import net.sf.josceleton.core.api.entity.location.RangeScaler;
import net.sf.josceleton.playground.motion.app2.framework.view.DrawSurface;

public class WorldSnapshotFactory {
	
	private final RangeScaler cursorToGlobalScaler;
	private final Range cursorToGlobalX;
	private final Range cursorToGlobalY;
	private final int gap;
	private final Joint cursorJoint;
	private final DrawSurface surface;
	
	public WorldSnapshotFactory(Joint cursorJoint, RangeScaler scaler, Range rangeX, Range rangeY, int gap, DrawSurface surface) {
		this.cursorJoint = cursorJoint;
		this.cursorToGlobalScaler = scaler;
		this.cursorToGlobalX = rangeX;
		this.cursorToGlobalY = rangeY;
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
		final Point cursorLocation = new Point(this.cursorToGlobalScaler.scale(coordinate.x(), this.cursorToGlobalX) + this.gap,
						 this.cursorToGlobalScaler.scale(coordinate.y(), this.cursorToGlobalY) + this.gap);
		return new WorldSnapshot(cursorLocation, skeleton, this.surface);
	}

	public WorldSnapshot createInitialDummy() {
		System.out.println("WorldSnapshotFactory: createInitialDummy()");
		return new WorldSnapshot(new Point(-1, -1)/*faked cursor location*/, null, this.surface);
	}
}
