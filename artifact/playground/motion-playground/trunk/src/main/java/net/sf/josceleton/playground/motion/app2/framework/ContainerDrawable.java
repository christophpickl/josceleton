package net.sf.josceleton.playground.motion.app2.framework;

import java.awt.Graphics2D;

public interface ContainerDrawable {
	
	void drawWithMaxSize(WorldSnapshot world, Graphics2D g, int width, int height);
	
}
