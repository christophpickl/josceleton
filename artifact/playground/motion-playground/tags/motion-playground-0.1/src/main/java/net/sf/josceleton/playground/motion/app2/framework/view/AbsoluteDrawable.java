package net.sf.josceleton.playground.motion.app2.framework.view;

import java.awt.Graphics2D;

import net.sf.josceleton.playground.motion.app2.framework.world.WorldSnapshot;

public interface AbsoluteDrawable {
	
	void draw(Graphics2D g, WorldSnapshot world);
	
}
