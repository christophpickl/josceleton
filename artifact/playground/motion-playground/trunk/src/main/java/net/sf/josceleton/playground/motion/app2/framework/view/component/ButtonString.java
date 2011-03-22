package net.sf.josceleton.playground.motion.app2.framework.view.component;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.Rectangle;

import net.sf.josceleton.playground.motion.app2.framework.view.common.Styles;
import net.sf.josceleton.playground.motion.app2.framework.world.WorldSnapshot;

public class ButtonString extends AbstractButton {
	
	private final Dimension size;
	
	public ButtonString(String label, final Dimension size) {
		super(null, label, size);
		this.size = size;
	}

	@Override
	public void _drawOnPosition(Graphics2D g, int x, int y, WorldSnapshot world) {
		g.setPaint(Color.BLUE);
		g.fillRect(x, y, this.size.width, this.size.height);
		g.setColor(Color.WHITE);
		g.setFont(Styles.FONT_REGULAR);
		g.drawString(this.getLabel(), x + 2, y + 2); // TODO exact position calculaten (wieviel platz braucht label?!)
	}

	@Override
	protected Rectangle updateHitArea(Rectangle hitArea, int x, int y) {
		if(hitArea == null) {
			return new Rectangle(x, y, this.size.width, this.size.height);
		}
		
		hitArea.x = x;
		hitArea.y = y;
		return hitArea;
	}

}
