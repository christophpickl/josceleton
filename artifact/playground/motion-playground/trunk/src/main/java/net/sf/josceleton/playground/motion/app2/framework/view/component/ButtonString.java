package net.sf.josceleton.playground.motion.app2.framework.view.component;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FontMetrics;
import java.awt.Graphics2D;
import java.awt.Rectangle;

import net.sf.josceleton.playground.motion.app2.framework.view.resources.Style;
import net.sf.josceleton.playground.motion.app2.framework.world.WorldSnapshot;

public class ButtonString extends AbstractButton {
	private static final Style.Text STYLE = Style.Text.BUTTON_LABEL;
	
	private final String label;
	private Dimension size;
	private Dimension backgroundSize;
	
	public ButtonString(String label) {
		this.label = label;
	}

	@Override
	public void _drawOnPosition(Graphics2D g, int x, int y, WorldSnapshot world) {
		g.setPaint(Color.BLUE);
		g.fillRect(x, y, this.backgroundSize.width, this.backgroundSize.height);
		
		STYLE.on(g);
		g.drawString(this.label, x, y + this.size.height);
	}

	@Override
	protected Rectangle updateHitArea(Rectangle hitArea, int x, int y, Graphics2D g) {
		if(this.size == null) { // delayed initialization as we need g to calculate label size
			STYLE.on(g);
			this.size = STYLE.calculateSize(this.label, g);
			
			this.backgroundSize = new Dimension(this.size.width, this.size.height + 10);
			return new Rectangle(x, y, this.size.width, this.size.height);
		}
		
		hitArea.x = x;
		hitArea.y = y;
		return hitArea;
	}

}
