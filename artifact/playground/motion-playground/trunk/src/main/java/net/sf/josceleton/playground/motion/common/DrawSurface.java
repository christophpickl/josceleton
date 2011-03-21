package net.sf.josceleton.playground.motion.common;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;

import javax.swing.JPanel;

import net.sf.josceleton.playground.motion.app2.framework.PageView;
import net.sf.josceleton.playground.motion.app2.framework.WorldChangedListener;
import net.sf.josceleton.playground.motion.app2.framework.WorldSnapshot;

public class DrawSurface extends JPanel implements WorldChangedListener {

	private static final long serialVersionUID = 8697192042852384195L;
	private static final int CURSOR_HALF_SIZE = 15;
	private static final Color CURSOR_COLOR = Color.WHITE;
	
	private PageView currentView;
	private WorldSnapshot recentWorld;
	
	
	public DrawSurface(final PageView startView) {
		this.currentView = startView;
	}
	
	public void overrideSize(final int width, final int height) {
		final Dimension d = new Dimension(width, height);
		this.setPreferredSize(d);
		this.setMinimumSize(d);
		this.setSize(d);
	}
	
	@Override public void paintComponent(final Graphics g) {
		super.paintComponent(g); 
		final Graphics2D g2 = (Graphics2D) g;
		
		if(this.recentWorld == null) {
			return; // wait until data has arrived
		}
		
		this.currentView.drawWithMaxSize(this.recentWorld, g2, this.getWidth(), this.getHeight());
		this.drawCursor(g2);
	}
	
	private void drawCursor(Graphics2D g2) {
		final Point locationRHand = this.recentWorld.getLocationRHand();
		if(locationRHand != null) {
			g2.setColor(CURSOR_COLOR);
			g2.drawLine(locationRHand.x, locationRHand.y - CURSOR_HALF_SIZE, locationRHand.x, locationRHand.y + CURSOR_HALF_SIZE);
			g2.drawLine(locationRHand.x - CURSOR_HALF_SIZE, locationRHand.y, locationRHand.x + CURSOR_HALF_SIZE, locationRHand.y);
		}
	}

	public void setView(final PageView view) {
		System.out.println("DrawSurface: setView(" + view + ")");
		this.currentView = view;
		this.repaint();
	}

	@Override public void onUpdated(WorldSnapshot world) {
		this.recentWorld = world;
		this.repaint();
	}
}
