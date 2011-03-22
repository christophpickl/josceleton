package net.sf.josceleton.playground.motion.app2;

import net.sf.josceleton.Josceleton;
import net.sf.josceleton.connection.api.Connection;
import net.sf.josceleton.connection.api.Connector;
import net.sf.josceleton.connection.api.service.motion.ContinuousMotionStream;
import net.sf.josceleton.connection.impl.service.motion.ContinuousMotionStreamFactory;
import net.sf.josceleton.core.api.entity.joint.Joint;
import net.sf.josceleton.core.api.entity.joint.Joints;
import net.sf.josceleton.core.api.entity.location.Range;
import net.sf.josceleton.playground.motion.app2.framework.Navigation;
import net.sf.josceleton.playground.motion.app2.framework.ThrottledMotionStream;
import net.sf.josceleton.playground.motion.app2.framework.page.PageManager;
import net.sf.josceleton.playground.motion.app2.framework.page.PageManagerListener;
import net.sf.josceleton.playground.motion.app2.framework.view.DrawSurface;
import net.sf.josceleton.playground.motion.app2.framework.view.component.Cursor;
import net.sf.josceleton.playground.motion.app2.framework.view.component.ImageCursor;
import net.sf.josceleton.playground.motion.app2.framework.world.WorldSnapshotFactory;
import net.sf.josceleton.playground.motion.common.UsersPanel;
import net.sf.josceleton.playground.motion.common.WindowX;
import net.sf.josceleton.playground.motion.common.WindowXListener;
import net.sf.josceleton.prototype.console.ConsolePrototypeModule;
import net.sf.josceleton.prototype.console.glue.ConsolePresenter;
import net.sf.josceleton.prototype.console.glue.ConsolePresenterFactory;
import net.sf.josceleton.prototype.console.view.ConsoleWindow;

import com.google.inject.Guice;
import com.google.inject.Injector;

public class App2 {
	public static void main(String[] args) {
		// TODO splash screen!
		new App2().start();
	}
	
	// FIXME pages sind alle Hibernateable (wenn sie zb intern animation/thread haben, dass das pausiert wird)
	private static final boolean FULLSCREEN_ENABLED = false;
//	private static final boolean FULLSCREEN_ENABLED = true;
	
	private static final int FPS = 24;
	
	final Connection connection;
	final ThrottledMotionStream throttledStream;
	final PageManager pageManager;
	final Navigation navigation;
	final WindowX window;
	final ConsoleWindow consoleWindow;
	
	public App2() {
	
		final Injector injector = Guice.createInjector(new ConsolePrototypeModule()); // ConsolePrototypeModule got already josceleton included
		final Connector connector = injector.getInstance(Connector.class);
		connection = connector.openConnection();
		
		final ContinuousMotionStream motionStream = injector.getInstance(ContinuousMotionStreamFactory.class).create(connection);
		throttledStream = new ThrottledMotionStream(motionStream, FPS);

		consoleWindow = injector.getInstance(ConsoleWindow.class);
		final ConsolePresenter consolePresenter = injector.getInstance(ConsolePresenterFactory.class).create(consoleWindow, connection);
		consolePresenter.init();
		consoleWindow.setLocation(1200, 500);
		consoleWindow.setVisible(true);
		
		
		final String idMain = "main";
		final String idGame = "game";
		final MainPage mainPage = new MainPage(idMain, idGame);
		navigation = new Navigation(mainPage.getId(), mainPage, new GamePage(idGame, idMain));
		
//		final Cursor cursor = new SimpleCursor();
		final Cursor cursor = new ImageCursor();
		final DrawSurface drawSurface = new DrawSurface(cursor);
		
		final UsersPanel usersPanel = new UsersPanel(connection.getUserService());
		connection.getUserService().addListener(usersPanel); // FIXME @API: UserServiceListener bekommt auch UsersColleciton uebergeben (so wie Skeleton auch funkt)!!!
		
		window = new WindowX(usersPanel, FULLSCREEN_ENABLED, drawSurface);
		window.addListener(new WindowXListener() {
			@Override public void onQuit() {
				doQuit();
			}});
		// setup cursor
		final Joint cursorJoint = Joints.HAND().LEFT();
		final int gap = 10;
		final Range rangeX = Josceleton.newRange(0.3F, 0.6F, 0, drawSurface.getWidth() - (2 * gap));
		final Range rangeY = Josceleton.newRange(0.2F, 0.6F, 0, drawSurface.getHeight() - (2 * gap));
		WorldSnapshotFactory factory = new WorldSnapshotFactory(cursorJoint, Josceleton.getRangeScaler(), rangeX, rangeY, gap, drawSurface);

		pageManager = new PageManager(navigation, throttledStream, drawSurface, factory);
		pageManager.addListener(new PageManagerListener() {
			@Override public void onQuit() {
				doQuit();
		}});
	}
	
	public void start() {
		pageManager.start();
		window.displayLater();
	}
	
	void doQuit() {
		window.setVisible(false);
		window.dispose();
		
		consoleWindow.setVisible(true);
		consoleWindow.dispose();
		
		throttledStream.close();
		connection.close();
	}
}
