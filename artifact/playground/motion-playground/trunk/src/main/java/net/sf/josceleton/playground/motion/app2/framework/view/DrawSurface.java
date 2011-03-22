package net.sf.josceleton.playground.motion.app2.framework.view;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;

import javax.swing.JPanel;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import net.sf.josceleton.core.api.entity.location.Direction;
import net.sf.josceleton.playground.motion.app2.framework.view.common.Style;
import net.sf.josceleton.playground.motion.app2.framework.view.component.Cursor;
import net.sf.josceleton.playground.motion.app2.framework.view.component.SkeletonDrawer;
import net.sf.josceleton.playground.motion.app2.framework.world.WorldChangedListener;
import net.sf.josceleton.playground.motion.app2.framework.world.WorldSnapshot;

public class DrawSurface implements WorldChangedListener {

	private static final long serialVersionUID = 8697192042852384195L;
	private static final Log LOG = LogFactory.getLog(DrawSurface.class);
	
//	private static final boolean SKELETON_ENABLED = true;
	private static final boolean SKELETON_ENABLED = false;
	
	private PageView currentView;
	
	private WorldSnapshot recentWorld;
	
	private final Cursor cursor;
	
	public DrawSurface(final Cursor cursor) {
		this.cursor = cursor;
	}
	
	private final JPanel internalUiComponent = new JPanel() {
		
		private static final long serialVersionUID = -896244269322870882L;
		private static final int SKELETON_WIDTH = 200;
		private static final int SKELETON_HEIGHT = 200;
		private static final int SKELETON_GAP = 10;
		private final SkeletonDrawer skeletonDrawerXy = new SkeletonDrawer(new Dimension(SKELETON_WIDTH, SKELETON_HEIGHT), Direction.X, Direction.Y);
		private final SkeletonDrawer skeletonDrawerXz = new SkeletonDrawer(new Dimension(SKELETON_WIDTH, SKELETON_HEIGHT), Direction.X, Direction.Z);
		private final SkeletonDrawer skeletonDrawerYz = new SkeletonDrawer(new Dimension(SKELETON_WIDTH, SKELETON_HEIGHT), Direction.Y, Direction.Z);
		
		@Override public void paintComponent(final Graphics g1) {
			super.paintComponent(g1); 
			final Graphics2D g = (Graphics2D) g1;
			paintInternalUiComponent(g);

			if(SKELETON_ENABLED == true) {
				final WorldSnapshot world = DrawSurface.this.recentWorld;
				if(world == null) {
					return;
				}
				final Point c = world.getCursorLocation();
				Style.Text.COMMENT.on(g);
				final int bottom = world.getHeight();
				g.drawString("x: " + c.x, 5, bottom - 35);
				g.drawString("y: " + c.y, 5, bottom - 10);
				
				this.skeletonDrawerXy.drawOnPosition(g, 50,                                       10, world);
				this.skeletonDrawerXz.drawOnPosition(g, 50 +      SKELETON_WIDTH + SKELETON_GAP , 10, world);
				this.skeletonDrawerYz.drawOnPosition(g, 50 + 2 * (SKELETON_WIDTH + SKELETON_GAP), 10, world);
			}
		}
	};
	
	void paintInternalUiComponent(Graphics2D g) {
		if(this.recentWorld == null) {
			return; // wait until data has arrived
		}
		
		this.currentView.drawWithMaxSize(this.recentWorld, g,
			this.internalUiComponent.getWidth(), this.internalUiComponent.getHeight());
		
		if(this.recentWorld.getSkeleton() != null) { // now we really got some data ;)
			this.cursor.draw(g, this.recentWorld);
		}
	}
	
	// FIXME if received userLost => clear view! (remove cursor, update skeleton drawer)
	// TODO if userLost, draw Red line and delay removal a bit
	// TODO new feature: support multiple cursors with multiple users

	@Override public void onUpdated(WorldSnapshot world) {
//		System.out.println("DrawSurface: ..... onUpdated with world: " + world);
		this.recentWorld = world;
		this.internalUiComponent.repaint();
	}

	public void setView(final PageView view) {
		LOG.info("setView(" + view + ")");
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
