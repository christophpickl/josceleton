package net.sf.josceleton.playground.motion.app2.framework.world;

import java.awt.Point;

import net.sf.josceleton.core.api.entity.joint.Skeleton;
import net.sf.josceleton.playground.motion.common.DrawSurface;

public class WorldSnapshot {
	
	private final Point locationRHand;
	private final Skeleton skeleton;
	private final DrawSurface surface;
	
	public WorldSnapshot(Point locationRHand, Skeleton skeleton, final DrawSurface surface) {
		this.locationRHand = locationRHand;
		this.skeleton = skeleton;
		this.surface = surface;
	}
	
	public Point getLocationRHand() {
		return this.locationRHand;
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
