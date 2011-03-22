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
		final Point cursor = world.getCursorLocation();
		if(cursor != null) {
			
			g.setColor(CURSOR_COLOR);
			g.drawLine(cursor.x, cursor.y - CURSOR_HALF_SIZE, cursor.x, cursor.y + CURSOR_HALF_SIZE);
			g.drawLine(cursor.x - CURSOR_HALF_SIZE, cursor.y, cursor.x + CURSOR_HALF_SIZE, cursor.y);
		}
	}

}
