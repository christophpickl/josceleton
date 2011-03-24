package net.sf.josceleton.playground.motion.app2.framework.view;

import java.awt.Graphics2D;

import net.sf.josceleton.playground.motion.app2.framework.world.WorldSnapshot;

public interface ContainerDrawable {
	
	void drawWithMaxSize(WorldSnapshot world, Graphics2D g, int width, int height);
	
}
