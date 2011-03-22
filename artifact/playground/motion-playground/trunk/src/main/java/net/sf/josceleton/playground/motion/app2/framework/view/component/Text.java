package net.sf.josceleton.playground.motion.app2.framework.view.component;

import java.awt.Graphics2D;

import net.sf.josceleton.playground.motion.app2.framework.view.Drawable;
import net.sf.josceleton.playground.motion.app2.framework.view.common.Style;
import net.sf.josceleton.playground.motion.app2.framework.world.WorldSnapshot;

public class Text implements Drawable {
	
	private final String string;
	
	private final Style.Text style;

	
	public Text(String string) {
		this(string, null);
	}
	
	public Text(String string, Style.Text style) {
		this.string = string;
		this.style = style;
	}

	@Override public void drawOnPosition(Graphics2D g, int x, int y, WorldSnapshot world) {
		this.style.on(g); // FIXME do for all Drawables!
		g.drawString(this.string, x, y);
	}
	
}
