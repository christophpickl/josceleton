package net.sf.josceleton.playground.motion.app2.framework.view.component;

import java.awt.Dimension;
import java.awt.Graphics2D;

import net.sf.josceleton.playground.motion.app2.framework.view.Drawable;
import net.sf.josceleton.playground.motion.app2.framework.view.resources.Style;
import net.sf.josceleton.playground.motion.app2.framework.world.WorldSnapshot;

public class Text implements Drawable {
	
	private final String label;
	
	private final Style.Text style;

	
	public Text(String string) {
		this(string, null);
	}
	
	public Text(String label, Style.Text style) {
		this.label = label;
		this.style = style;
	}

	@Override public void drawOnPosition(Graphics2D g, int x, int y, WorldSnapshot world) {
		this.style.on(g); // FIXME do for all Drawables!
		g.drawString(this.label, x, y);
	}
	
	public final Style.Text getStyle() {
		return this.style;
	}

	public Dimension calculateSize(Graphics2D g) {
		this.style.on(g); // FIXME duplicate invocation! but was necessary to get proper calculated size back (client should have not done this by himself..)
		return this.style.calculateSize(this.label, g);
	}
	
}
