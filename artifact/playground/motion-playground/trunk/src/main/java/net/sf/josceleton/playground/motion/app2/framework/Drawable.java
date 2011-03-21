package net.sf.josceleton.playground.motion.app2.framework;

import java.awt.Graphics2D;

public interface Drawable {
	
	void drawOnPosition(WorldSnapshot world, Graphics2D g, int x, int y);
	
}
