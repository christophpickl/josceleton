package net.sf.josceleton.playground.motion.app2.framework.view.component;

import java.awt.Graphics2D;

import net.sf.josceleton.playground.motion.app2.framework.view.Drawable;
import net.sf.josceleton.playground.motion.app2.framework.world.WorldSnapshot;

public class Text implements Drawable {
	
	private final String string;

	public Text(String string) {
		this.string = string;
	}

	@Override public void drawOnPosition(Graphics2D g, int x, int y, WorldSnapshot world) {
		g.drawString(this.string, x, y);
	}
	
}
