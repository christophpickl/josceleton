package net.sf.josceleton.playground.motion.common;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;

import javax.swing.JPanel;

import net.sf.josceleton.playground.motion.app2.framework.view.PageView;
import net.sf.josceleton.playground.motion.app2.framework.world.WorldChangedListener;
import net.sf.josceleton.playground.motion.app2.framework.world.WorldSnapshot;

public class DrawSurface implements WorldChangedListener {

	private static final long serialVersionUID = 8697192042852384195L;
	
	private PageView currentView;
	
	private WorldSnapshot recentWorld;
	
	
	
	private final JPanel internalUiComponent = new JPanel() {
		private static final long serialVersionUID = -896244269322870882L;
		@Override public void paintComponent(final Graphics g) {
			super.paintComponent(g); 
			paintInternalUiComponent((Graphics2D) g);
		}
	};
	
	void paintInternalUiComponent(Graphics2D g) {
		if(this.recentWorld == null) {
			return; // wait until data has arrived
		}
		this.currentView.drawWithMaxSize(this.recentWorld, g,
			this.internalUiComponent.getWidth(), this.internalUiComponent.getHeight());
		
//		FIXME this.drawCursor(g);
	}

	@Override public void onUpdated(WorldSnapshot world) {
		this.recentWorld = world;
		this.internalUiComponent.repaint();
	}

	public void setView(final PageView view) {
		System.out.println("DrawSurface: setView(" + view + ")");
		this.currentView = view;
		this.internalUiComponent.repaint();
	}
	
	
	public Component asComponent() {
		return this.internalUiComponent;
	}

	public void enforceSize(int width, int height) {
		final Dimension d = new Dimension(width, height);
		this.internalUiComponent.setPreferredSize(d);
		this.internalUiComponent.setMinimumSize(d);
		this.internalUiComponent.setSize(d);
	}

	public int getWidth() {
		return this.internalUiComponent.getWidth();
	}

	public int getHeight() {
		return this.internalUiComponent.getHeight();
	}
	
}
