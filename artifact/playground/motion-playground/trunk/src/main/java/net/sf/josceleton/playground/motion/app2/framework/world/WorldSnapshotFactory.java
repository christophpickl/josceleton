package net.sf.josceleton.playground.motion.app2.framework.world;

import java.awt.Point;

import net.sf.josceleton.core.api.entity.joint.Joint;
import net.sf.josceleton.core.api.entity.joint.Skeleton;
import net.sf.josceleton.core.api.entity.location.Coordinate;
import net.sf.josceleton.core.api.entity.location.Range;
import net.sf.josceleton.core.api.entity.location.RangeScaler;
import net.sf.josceleton.playground.motion.app2.framework.view.DrawSurface;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class WorldSnapshotFactory {
	
	private static final Log LOG = LogFactory.getLog(WorldSnapshotFactory.class);
	
//	private final CoordinateStabilizer cursorStabilizer = new CoordinateStabilizer();
	
	private final Joint cursorJoint;
	private final ScalerAndRanges scaler;
	private final WorldSnapshot singleInstance;
	public WorldSnapshotFactory(Joint cursorJoint, ScalerAndRanges scaler, DrawSurface surface) {
		this.cursorJoint = cursorJoint;
		this.scaler = scaler;
		
		this.singleInstance = new WorldSnapshot(surface, scaler);
	}
	public static class ScalerAndRanges {

		private final RangeScaler cursorToGlobalScaler;
		private final Range cursorToGlobalX;
		private final Range cursorToGlobalY;
		private final int xyOffset;
		public ScalerAndRanges(RangeScaler cursorToGlobalScaler,
				Range cursorToGlobalX, Range cursorToGlobalY, int xyOffset) {
			this.cursorToGlobalScaler = cursorToGlobalScaler;
			this.cursorToGlobalX = cursorToGlobalX;
			this.cursorToGlobalY = cursorToGlobalY;
			this.xyOffset = xyOffset;
		}
		public Point scale(float rawX, float rawY) {
			return new Point(this.cursorToGlobalScaler.scale(rawX, this.cursorToGlobalX) + this.xyOffset,
					   this.cursorToGlobalScaler.scale(rawY, this.cursorToGlobalY) + this.xyOffset);
		}
		public Point scale(Coordinate coordinate) {
			return this.scale(coordinate.x(), coordinate.y());
		}
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
		
//		final int[] xy = this.cursorStabilizer.stabilize(
//				this.cursorToGlobalScaler.scale(coordinate.x(), this.cursorToGlobalX),
//				this.cursorToGlobalScaler.scale(coordinate.y(), this.cursorToGlobalY));
//		final Point cursorLocation = new Point(xy[0] + this.gap, xy[1] + this.gap);
		
		final Point cursorLocation = this.scaler.scale(coordinate);
//		final Point cursorLocation = new Point(this.cursorToGlobalScaler.scale(coordinate.x(), this.cursorToGlobalX) + this.gap,
//											   this.cursorToGlobalScaler.scale(coordinate.y(), this.cursorToGlobalY) + this.gap);
		
//		LOG.warn("coordinate => "+coordinate+"....... cursor => " + cursorLocation);
		// coordinate => CoordinateImpl[x=0.6082467, y=0.4017404, z=1.2116178]....... cursor => java.awt.Point[x=-10,y=0]
		
		this.singleInstance.setCursorLocation(cursorLocation);
		this.singleInstance.setSkeleton(skeleton);
		return this.singleInstance;
	}
	
	public WorldSnapshot createInitialDummy() {
		LOG.info("createInitialDummy()");
		this.singleInstance.setCursorLocation(new Point(-1, -1)/*faked cursor location*/);
		return this.singleInstance;
	}
}
