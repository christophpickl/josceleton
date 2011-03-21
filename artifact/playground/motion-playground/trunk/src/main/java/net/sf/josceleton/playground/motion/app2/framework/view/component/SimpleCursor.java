package net.sf.josceleton.playground.motion.app2.framework.view.component;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Point;

import net.sf.josceleton.playground.motion.app2.framework.world.WorldSnapshot;

public class SimpleCursor implements Cursor {

	private static final int CURSOR_HALF_SIZE = 15;
	private static final Color CURSOR_COLOR = Color.WHITE;
	
	@Override
	public void draw(Graphics2D g, WorldSnapshot world) {
		final Point locationRHand = world.getLocationRHand();
		if(locationRHand != null) {
			
			g.setColor(CURSOR_COLOR);
			g.drawLine(locationRHand.x, locationRHand.y - CURSOR_HALF_SIZE, locationRHand.x, locationRHand.y + CURSOR_HALF_SIZE);
			g.drawLine(locationRHand.x - CURSOR_HALF_SIZE, locationRHand.y, locationRHand.x + CURSOR_HALF_SIZE, locationRHand.y);
		}
	}

}
