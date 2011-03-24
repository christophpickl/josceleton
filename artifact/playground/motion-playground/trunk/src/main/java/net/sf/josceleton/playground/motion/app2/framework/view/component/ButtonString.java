package net.sf.josceleton.playground.motion.app2.framework.view.component;

import java.awt.AlphaComposite;
import java.awt.Composite;
import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.Rectangle;

import net.sf.josceleton.playground.motion.app2.framework.view.resources.Style;
import net.sf.josceleton.playground.motion.app2.framework.world.WorldSnapshot;

public class ButtonString extends AbstractButton {
	
	public static final int DISABLE_DEFAULT_SIZE = -42;
	private static final int DEFAULT_SIZE = 220;
	public static final int DEFAULT_HALF_WIDTH = DEFAULT_SIZE / 2;
	
	private static final Style.Shape BOX_LINE_STYLE = Style.Shape.BUTTON_BOX_LINE;
	private static final Style.Shape BOX_BG_STYLE = Style.Shape.BUTTON_BOX_BG;
	private static final Style.Text LABEL_STYLE = Style.Text.BUTTON_LABEL;
	
	private static final int BOX_ROUND_EDGE = 50;
	private static final int BOX_INSET_X = 30;
	
	private static final int BOX_INSET_TOP = 10; // seems as there is a strange line height computation => separately set top/bottom inset
	private static final int BOX_INSET_TOP_BOTTOM_DIFF = 12;
	private static final int BOX_INSET_BOT = BOX_INSET_TOP + BOX_INSET_TOP_BOTTOM_DIFF;
	
	private final String label;
	private Dimension labelSize;
	private Dimension boxSize;
	private int enforcedButtonWidth;
	private int labelCenterAlignedPadding = 0; // by default; if not enforced width
	
	public ButtonString(String label) {
		this(label, DEFAULT_SIZE);
	}
	
	public ButtonString(String label, int enforcedButtonWidthOrDisableDefaultSizeFlag) {
		this.label = label;
		this.enforcedButtonWidth = enforcedButtonWidthOrDisableDefaultSizeFlag;
	}

	@Override
	public void _drawOnPosition(Graphics2D g, int x, int y, WorldSnapshot world) {
		
		final Composite oldComposite = g.getComposite();
		AlphaComposite myAlpha = AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.5F);
		g.setComposite(myAlpha);
		BOX_BG_STYLE.on(g);
		g.fillRoundRect(x, y, this.boxSize.width, this.boxSize.height, BOX_ROUND_EDGE, BOX_ROUND_EDGE);
		g.setComposite(oldComposite); // TODO damn it, we have to reset composite :(
		
		BOX_LINE_STYLE.on(g);
		g.drawRoundRect(x, y, this.boxSize.width, this.boxSize.height, BOX_ROUND_EDGE, BOX_ROUND_EDGE);
		
		LABEL_STYLE.on(g);
		g.drawString(this.label, x + BOX_INSET_X + /* center-aligned */ this.labelCenterAlignedPadding,
								 y + BOX_INSET_TOP + this.labelSize.height);
	}

	@Override
	protected Rectangle updateHitArea(Rectangle hitArea, int x, int y, Graphics2D g) {
		if(this.labelSize == null) { // delayed initialization as we need g to calculate label size
			LABEL_STYLE.on(g); // apply style, so calculation gets proper style values
			final Dimension calculatedLabelSize = LABEL_STYLE.calculateSize(this.label, g);
			if(this.enforcedButtonWidth != DISABLE_DEFAULT_SIZE) {
				this.labelSize = new Dimension(this.enforcedButtonWidth, calculatedLabelSize.height);
			} else {
				this.labelSize = calculatedLabelSize;
			}
			
			this.boxSize = new Dimension(this.labelSize.width + 2*BOX_INSET_X,
												this.labelSize.height + BOX_INSET_TOP + BOX_INSET_BOT);
			if(this.enforcedButtonWidth != DISABLE_DEFAULT_SIZE) {
				this.labelCenterAlignedPadding = (this.boxSize.width - 2*BOX_INSET_X - calculatedLabelSize.width) / 2;
			}
			
			return new Rectangle(x, y, this.boxSize.width, this.boxSize.height);
		}
		
		hitArea.x = x;
		hitArea.y = y;
		return hitArea;
	}

	@Override
	public int calculateHalfWidth(Graphics2D g) {
		return LABEL_STYLE.calculateSize(this.label, g).width / 2;
	}

	@Override
	public int calculateHeight(Graphics2D g) {
		return LABEL_STYLE.calculateSize(this.label, g).height;
	}

}
