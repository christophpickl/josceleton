package net.sf.josceleton.playground.motion.app2.framework.view.component;

import java.awt.Graphics2D;
import java.awt.Image;

import net.sf.josceleton.playground.motion.app2.framework.view.common.Resources;
import net.sf.josceleton.playground.motion.app2.framework.world.WorldSnapshot;

public class ImageCursor implements Cursor {
	
	private static final Image IMAGE = Resources.CURSOR;
	private static final int X_ADJUSTMENT = -19;
	private static final int Y_ADJUSTMENT = -3;
	
	@Override public void draw(Graphics2D g, WorldSnapshot world) {
		final int x = world.getCursorLocation().x + X_ADJUSTMENT;
		final int y = world.getCursorLocation().y + Y_ADJUSTMENT;
		g.drawImage(IMAGE, x, y, null);
	}

}
