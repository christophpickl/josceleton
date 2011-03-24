package net.sf.josceleton.playground.motion.app1;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Toolkit;

import javax.swing.JPanel;

import net.sf.josceleton.Josceleton;
import net.sf.josceleton.core.api.async.Listener;
import net.sf.josceleton.core.api.entity.joint.Joint;
import net.sf.josceleton.core.api.entity.joint.Joints;
import net.sf.josceleton.core.api.entity.joint.Skeleton;
import net.sf.josceleton.core.api.entity.location.Coordinate;
import net.sf.josceleton.core.api.entity.location.Range;
import net.sf.josceleton.core.api.entity.location.RangeScaler;

public class ContentPanel extends JPanel {
	private static final long serialVersionUID = -5402622339724289124L;
	
	private static final int GAP = 10;
	
	private final RangeScaler scaler = Josceleton.getRangeScaler();
	private final Range rangeX;
	private final Range rangeY;
	
	private final JosceletonButton hitBox;
	private Point locationLHand;
	
	public ContentPanel(final int panelWidth, final int panelHeight) {
		this.rangeX = Josceleton.newRange(0.3F, 0.6F, 0, panelWidth - (2 * GAP));
		this.rangeY = Josceleton.newRange(0.2F, 0.6F, 0, panelHeight - (2 * GAP));
		this.hitBox = new JosceletonButton(new Point(panelWidth / 2, panelHeight / 2), new Dimension(80, 80));
		this.hitBox.addListener(new ViewComponentListener() {
			@Override public void onClicked(JosceletonButton josceletonButton) {
				System.out.println("MOUSE CLICK MOUSE CLICK MOUSE CLICK MOUSE CLICK MOUSE CLICK");
				Toolkit.getDefaultToolkit().beep();
			}
		});
		final Dimension d = new Dimension(panelWidth, panelHeight);
		this.setPreferredSize(d);
		this.setMinimumSize(d);
		this.setSize(d);
		
		this.setBackground(Color.BLACK);
	}
	
	@Override public void paintComponent(final Graphics g) {
		super.paintComponent(g); 
		Graphics2D g2 = (Graphics2D) g;
		
		// TODO gewaehrleisten, dass die GUI NIEMALS null bekommt, indem man einfach am anfang mal wartet bis fuer alle joints coordinaten da sind ;)
		if(this.locationLHand != null) this.hitBox.makeHitTest(this.locationLHand);
		
		g2.setPaint(this.hitBox.getColor());
		g2.fillRect(this.hitBox.x, this.hitBox.y, this.hitBox.width, this.hitBox.height);
		
		if(this.locationLHand != null) {
			final int cursorHalfSize = 15;
			g2.setColor(Color.YELLOW);
			g2.drawLine(this.locationLHand.x, this.locationLHand.y - cursorHalfSize, this.locationLHand.x, this.locationLHand.y + cursorHalfSize);
			g2.drawLine(this.locationLHand.x - cursorHalfSize, this.locationLHand.y, this.locationLHand.x + cursorHalfSize, this.locationLHand.y);
		}
	}
	
	static interface ViewComponentListener extends Listener {

		void onClicked(JosceletonButton josceletonButton);
		
	}
	
	public void update(final Skeleton skeleton) {
		this.locationLHand = this.loadPoint(skeleton, Joints.HAND().LEFT());
		this.repaint();
	}
	
	private Point loadPoint(Skeleton skeleton, Joint joint) {
		if(skeleton.isCoordinateAvailable(joint) == false) {
			return null;
		}
		final Coordinate coordinate = skeleton.getNullSafe(joint);
		return new Point(this.scaler.scale(coordinate.x(), this.rangeX) + GAP,
						 this.scaler.scale(coordinate.y(), this.rangeY) + GAP);
	}
}
