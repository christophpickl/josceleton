package net.sf.josceleton.playground.motion.app2.framework.world;

import java.awt.Point;

import net.sf.josceleton.core.api.entity.joint.Skeleton;
import net.sf.josceleton.core.api.entity.location.Coordinate;
import net.sf.josceleton.playground.motion.app2.framework.view.DrawSurface;
import net.sf.josceleton.playground.motion.app2.framework.world.WorldSnapshotFactory.ScalerAndRanges;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class WorldSnapshot {
	
	private static final Log LOG = LogFactory.getLog(WorldSnapshot.class);
	
	private final DrawSurface surface;
	private final ScalerAndRanges scaler;
	
	private transient Point cursorLocation;
	private transient Skeleton skeleton;
	
	private transient boolean cursorVisible = true;
	
	public WorldSnapshot(final DrawSurface surface, ScalerAndRanges scaler) {
		this.surface = surface;
		this.scaler = scaler;
	}
	
	public Point getCursorLocation() {
		return this.cursorLocation;
	}
	
	void setCursorLocation(Point cursorLocation) {
		this.cursorLocation = cursorLocation;
	}

	public Skeleton getSkeleton() {
		return this.skeleton;
	}
	
	public void setSkeleton(Skeleton skeleton) {
		this.skeleton = skeleton;
	}
	

	public int getWidth() {
		return this.surface.getWidth();
	}

	public int getHeight() {
		return this.surface.getHeight();
	}

	public int getHorizontalCenter() {
		return this.surface.getWidth() / 2;
	}

	public int getVerticalCenter() {
		return this.surface.getHeight() / 2;
	}

	public Point transformCoordinate(Coordinate coordinate) {
		return this.scaler.scale(coordinate);
	}

	public Point transformCoordinate(float rawX, float rawY) {
		return this.scaler.scale(rawX, rawY);
	}
	
	public void setCursorVisible(boolean cursorVisible) {
		LOG.info("setCursorVisible("+cursorVisible+")");
		this.cursorVisible = cursorVisible;
	}

	public boolean isCursorVisible() {
		return this.cursorVisible;
	}
	
}
