package net.sf.josceleton.playground.motion.app2.framework.world;

import java.awt.Point;

import net.sf.josceleton.core.api.entity.joint.Skeleton;
import net.sf.josceleton.playground.motion.app2.framework.view.DrawSurface;

public class WorldSnapshot {
	
	private final Point cursorLocation;
	private final Skeleton skeleton;
	private final DrawSurface surface;
	
	public WorldSnapshot(Point cursorLocation, Skeleton skeleton, final DrawSurface surface) {
		this.cursorLocation = cursorLocation;
		this.skeleton = skeleton;
		this.surface = surface;
	}
	
	public Point getCursorLocation() {
		return this.cursorLocation;
	}
	
	public Skeleton getSkeleton() {
		return this.skeleton;
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
	
}
