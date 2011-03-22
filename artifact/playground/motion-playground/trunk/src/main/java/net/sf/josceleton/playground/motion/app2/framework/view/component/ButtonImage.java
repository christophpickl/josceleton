package net.sf.josceleton.playground.motion.app2.framework.view.component;

import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Rectangle;

import net.sf.josceleton.playground.motion.app2.framework.world.WorldSnapshot;

public class ButtonImage extends AbstractButton {

	private final Image image;
	
	public ButtonImage(Image image) {
		this.image = image;
	}
	
	// maybe move x/y coordinates into constructor (could set area instance final and hittest would need no null check)
	@Override public void _drawOnPosition(Graphics2D g, int x, int y, WorldSnapshot world) {
	    g.drawImage(this.image, x, y, null);
	    g.finalize();
	}
	
	@Override
	protected Rectangle updateHitArea(Rectangle hitArea, int x, int y, Graphics2D g) {
		if(hitArea == null) {
			return new Rectangle(x, y, this.image.getWidth(null), this.image.getHeight(null));
		}
		hitArea.x = x;
		hitArea.y = y;
		return hitArea;
	}
}
