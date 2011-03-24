package net.sf.josceleton.playground.motion.app2.framework.view;

import java.awt.Graphics2D;

import net.sf.josceleton.playground.motion.app2.framework.world.WorldSnapshot;

public interface Drawable {
	
	void drawOnPosition(Graphics2D g, int x, int y, WorldSnapshot world);
	
}
