package net.sf.josceleton.playground.motion.app2;

import java.awt.Dimension;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

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
import net.sf.josceleton.playground.motion.app2.framework.world.WorldSnapshotFactory.ScalerAndRanges;
import net.sf.josceleton.playground.motion.app2.game1.GameOverPage;
import net.sf.josceleton.playground.motion.app2.game1.GamePlayingPage;
import net.sf.josceleton.playground.motion.app2.game1.MenuPage;
import net.sf.josceleton.playground.motion.common.UsersPanel;
import net.sf.josceleton.playground.motion.common.WindowX;
import net.sf.josceleton.playground.motion.common.WindowXListener;
import net.sf.josceleton.prototype.console.ConsolePrototypeModule;
import net.sf.josceleton.prototype.console.util.CloseableUtil;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.google.inject.Guice;
import com.google.inject.Injector;

public class App2 {
	
	public static void main(String[] args) {
		// TODO splash screen!
		try {
			new App2().start();
		} catch(Exception e) {
			System.err.println("Could not start up application :(");
			e.printStackTrace();
			System.exit(1);
		}
	}
	private static final Log LOG = LogFactory.getLog(App2.class);
	// FIXME pages sind alle Hibernateable (wenn sie zb intern animation/thread haben, dass das pausiert wird)
//	private static final boolean FULLSCREEN_ENABLED = false;
	private static final boolean FULLSCREEN_ENABLED = true;
	
//	private static final int FPS = 24;
	
	final Connection connection;
	final ThrottledMotionStream throttledStream;
	final PageManager pageManager;
	final Navigation navigation;
	final WindowX window;
//	final ConsoleWindow consoleWindow;
	
	public App2() {
		final Properties p = loadPropertiesFromClassPath(App2.class.getClassLoader(), "app.properties");
		final String applicationVersion = p.get("app_version").toString();
		
		final Injector injector = Guice.createInjector(new ConsolePrototypeModule()); // ConsolePrototypeModule got already josceleton included
		final Connector connector = injector.getInstance(Connector.class);
		this.connection = connector.openConnection();
		
		final ContinuousMotionStream motionStream = injector.getInstance(ContinuousMotionStreamFactory.class).create(this.connection);
		this.throttledStream = new ThrottledMotionStream(motionStream);

//		consoleWindow = injector.getInstance(ConsoleWindow.class);
//		final ConsolePresenter consolePresenter = injector.getInstance(ConsolePresenterFactory.class).create(consoleWindow, connection);
//		consolePresenter.init();
//		consoleWindow.setLocation(1200, 500);
//		consoleWindow.setVisible(true);
		
		final MenuPage mainPage = new MenuPage();
		this.navigation = new Navigation(mainPage.getId(), mainPage, new GamePlayingPage(), new GameOverPage());
		
//		final Cursor cursor = new SimpleCursor();
		final Cursor cursor = new ImageCursor();
		final DrawSurface drawSurface = new DrawSurface(cursor);
		
		final UsersPanel usersPanel = new UsersPanel(this.connection.getUserService());
		this.connection.getUserService().addListener(usersPanel); // FIXME @API: UserServiceListener bekommt auch UsersColleciton uebergeben (so wie Skeleton auch funkt)!!!
		
		final String subtitle = "Josceleton Boxing Prototype v" + applicationVersion;
		this.window = new WindowX(usersPanel, FULLSCREEN_ENABLED, drawSurface, subtitle);
		this.window.addListener(new WindowXListener() {
			@Override public void onQuit() {
				doQuit();
			}});
		// setup cursor
		final Joint cursorJoint = Joints.HAND().LEFT();
		final int gap = 10;
		
		final int actualScreenWidth;
		final int actualScreenHeight;
		if(FULLSCREEN_ENABLED == true) {
			final Dimension fullScreenSize = this.window.getMonitorSize();
			actualScreenWidth = fullScreenSize.width;
			actualScreenHeight = fullScreenSize.height;
		} else {
			actualScreenWidth = drawSurface.getWidth();
			actualScreenHeight = drawSurface.getHeight();
		}
		
		LOG.info("actualScreen.size => " + actualScreenWidth + "x" + actualScreenHeight);
		
		final Range rangeX = Josceleton.newRange(0.3F, 0.6F, 0, actualScreenWidth - (2 * gap));
		final Range rangeY = Josceleton.newRange(0.2F, 0.6F, 0, actualScreenHeight - (2 * gap));
		ScalerAndRanges scaler = new ScalerAndRanges(Josceleton.getRangeScaler(), rangeX, rangeY, gap);
		WorldSnapshotFactory factory = new WorldSnapshotFactory(cursorJoint, scaler, drawSurface);

		this.pageManager = new PageManager(this.navigation, this.throttledStream, drawSurface, this.window, factory);
		this.pageManager.addListener(new PageManagerListener() {
			@Override public void onQuit() {
				doQuit();
		}});
	}
	
	public void start() {
		this.pageManager.start();
		this.window.displayLater();
	}
	
	void doQuit() {
		LOG.info("doQuit()");
		this.window.setVisible(false);
		this.window.dispose();
		this.pageManager.close();
		
//		consoleWindow.setVisible(true);
//		consoleWindow.dispose();
		
		this.throttledStream.close();
		this.connection.close();
	}
	
	// FIXME copied from midi-prototype SomeUtil
	public static Properties loadPropertiesFromClassPath(final ClassLoader loader, final String fileName) {
		final Properties properties = new Properties();
		InputStream inputStream = null;
		try {
			inputStream = loader.getResourceAsStream(fileName);
			if(inputStream == null) {
				throw new RuntimeException("Could not get resource [" + fileName + "] as stream!");
			}
			properties.load(inputStream);
			return properties;
		} catch(IOException e) {
			throw new RuntimeException("Could not load properties file [" + fileName +"]!", e);
		} finally {
			CloseableUtil.close(inputStream);
		}
	}
}
