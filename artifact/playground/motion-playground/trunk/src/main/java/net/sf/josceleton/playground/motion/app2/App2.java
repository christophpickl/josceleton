package net.sf.josceleton.playground.motion.app2;

import java.awt.Image;
import java.awt.Toolkit;
import java.net.URL;

import javax.swing.ImageIcon;

import net.sf.josceleton.Josceleton;
import net.sf.josceleton.connection.api.Connection;
import net.sf.josceleton.connection.api.service.motion.ContinuousMotionStream;
import net.sf.josceleton.core.api.entity.joint.Joints;
import net.sf.josceleton.core.api.entity.location.Range;
import net.sf.josceleton.playground.motion.ImageLoadingApp;
import net.sf.josceleton.playground.motion.app2.framework.LoginPage;
import net.sf.josceleton.playground.motion.app2.framework.Navigation;
import net.sf.josceleton.playground.motion.app2.framework.PageManager;
import net.sf.josceleton.playground.motion.app2.framework.WorldSnapshotFactory;
import net.sf.josceleton.playground.motion.common.DrawSurface;
import net.sf.josceleton.playground.motion.common.ThrottledMotionStream;
import net.sf.josceleton.playground.motion.common.WindowX;
import net.sf.josceleton.playground.motion.common.WindowXListener;

public class App2 {
	public static void main(String[] args) {
		new App2().start();
	}
	
	private static final int FPS = 24;
	
	public void start() {
		final Connection connection = Josceleton.openConnection();
		final ContinuousMotionStream motionStream = Josceleton.getContinuousMotionStreamFactory().create(connection);
		
		final ThrottledMotionStream throttledStream = new ThrottledMotionStream(motionStream, FPS);
		
		final Image continueImage = this.lookupImage("/image/next.png");
		final Navigation navigation = new Navigation(new LoginPage("asdf", continueImage));
		final DrawSurface drawSurface = new DrawSurface(navigation.getStartPage().getView());
		
		final MainWindow window = new MainWindow(drawSurface, new WindowXListener() {
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
	
	private Image lookupImage(String path) {
//		final Image foundImage = Toolkit.getDefaultToolkit().getImage(path);
//		if(foundImage == null) {
//			throw new RuntimeException("Could not find image: [" + path + "]!");
//		}
//		return foundImage;
		final URL url = ImageLoadingApp.class.getResource(path);
		System.out.println("url: " + url);
		final ImageIcon img = new ImageIcon(url);
		return img.getImage();
	}
}
