package net.sf.josceleton.playground.motion.app2;

import java.awt.Color;
import java.awt.Graphics2D;

import net.sf.josceleton.Josceleton;
import net.sf.josceleton.connection.api.Connection;
import net.sf.josceleton.connection.api.service.motion.ContinuousMotionStream;
import net.sf.josceleton.core.api.entity.joint.Joints;
import net.sf.josceleton.core.api.entity.location.Range;
import net.sf.josceleton.playground.motion.app2.framework.Navigation;
import net.sf.josceleton.playground.motion.app2.framework.page.Page;
import net.sf.josceleton.playground.motion.app2.framework.page.PageManager;
import net.sf.josceleton.playground.motion.app2.framework.view.AbstractPageView;
import net.sf.josceleton.playground.motion.app2.framework.world.WorldSnapshot;
import net.sf.josceleton.playground.motion.app2.framework.world.WorldSnapshotFactory;
import net.sf.josceleton.playground.motion.common.DrawSurface;
import net.sf.josceleton.playground.motion.common.ThrottledMotionStream;
import net.sf.josceleton.playground.motion.common.WindowX;
import net.sf.josceleton.playground.motion.common.WindowXListener;

public class App2 {
	public static void main(String[] args) {
		new App2().start();
	}
	
	// FIXME pages sind alle Hibernateable (wenn sie zb intern animation/thread haben, dass das pausiert wird)
	private static final boolean FULLSCREEN_ENABLED = false;
	
	private static final int FPS = 24;
	
	public void start() {
		final Connection connection = Josceleton.openConnection();
		final ContinuousMotionStream motionStream = Josceleton.getContinuousMotionStreamFactory().create(connection);
		
		final ThrottledMotionStream throttledStream = new ThrottledMotionStream(motionStream, FPS);
		
		final MainPage mainPage = new MainPage("main");
		final Navigation navigation = new Navigation(mainPage.getId(), mainPage);
//		final DrawSurface drawSurface = new DrawSurface(navigation.getStartPage().getView());
		final DrawSurface drawSurface = new DrawSurface();
		
		final WindowX window = new WindowX(FULLSCREEN_ENABLED, drawSurface, new WindowXListener() {
			@Override public void onQuit(WindowX quittingWindow) {
				quittingWindow.setVisible(false);
				quittingWindow.dispose();
				throttledStream.close();
				connection.close();
		}});
		
		final int gap = 10;
		final Range rangeX = Josceleton.newRange(0.3F, 0.6F, 0, drawSurface.getWidth() - (2 * gap));
		final Range rangeY = Josceleton.newRange(0.2F, 0.6F, 0, drawSurface.getHeight() - (2 * gap));
		WorldSnapshotFactory factory = new WorldSnapshotFactory(Joints.HAND().LEFT(), Josceleton.getRangeScaler(), rangeX, rangeY, gap, drawSurface);
		
		final PageManager pageManager = new PageManager(navigation, throttledStream, drawSurface, factory);
		pageManager.start();
		
		window.displayLater();
	}
	
	static class MainPage extends Page {
		public MainPage(String id) {
			super(id, new AbstractPageView() {
				@Override public void drawWithMaxSize(WorldSnapshot world, Graphics2D g, int width, int height) {
					g.setColor(Color.PINK);
					g.drawString("YES", world.getHorizontalCenter(), world.getVerticalCenter());
				}
			});
		}
	}
	
}
