package net.sf.josceleton.playground.motion.app1;

import java.awt.Dimension;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.Toolkit;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.Timer;
import java.util.TimerTask;

import net.sf.josceleton.Josceleton;
import net.sf.josceleton.connection.api.Connection;
import net.sf.josceleton.connection.api.service.motion.ContinuousMotionStream;
import net.sf.josceleton.connection.api.service.motion.MotionStreamListener;
import net.sf.josceleton.core.api.entity.joint.Joint;
import net.sf.josceleton.core.api.entity.joint.Skeleton;
import net.sf.josceleton.core.api.entity.location.Coordinate;
import net.sf.josceleton.playground.motion.app1.MainWindow.MainWindowListener;

public class App1 {
	public static void main(String[] args) { new App1().start(); }
	
	private static final int FPS = 20;
	
	private Skeleton skeleton;
	
	public void start() {
		final Connection connection = Josceleton.openConnection();
		final ContinuousMotionStream motionStream = Josceleton.getContinuousMotionStreamFactory().create(connection);
		
		final GraphicsEnvironment env = GraphicsEnvironment.getLocalGraphicsEnvironment();
		System.out.println("Available monitors: " + env.getScreenDevices().length);
		final GraphicsDevice device = env.getDefaultScreenDevice();
		
		final Timer timer = new Timer();
		
		final MainWindow window = new MainWindow(device.getDefaultConfiguration().getBounds().getSize(),
			new MainWindowListener() { @Override public void onQuit(MainWindow w) {
				System.out.println("App1.onQuit()");
//				device.setFullScreenWindow(null);
				timer.cancel();
				connection.close();
				w.dispose();
		}});
		if(device.isFullScreenSupported() == false) {
			System.err.println("Fullscreen not supported");
			return;
		}
		motionStream.addListener(new MotionStreamListener() {
			@Override public void onMoved(Joint movedJoint, Coordinate updatedCoordinate, Skeleton skeletonX) {
				skeleton = skeletonX;
			}
		});
		timer.scheduleAtFixedRate(new TimerTask() { @Override public void run() {
			if(skeleton != null) {
				window.updateSkeleton(skeleton);
			}
		}}, 0, 1000 / FPS);
		
		
		System.out.println("running");
//		device.setFullScreenWindow(window);
		window.pack();
		final Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		window.setLocation((screenSize.width - window.getWidth()) / 2, (screenSize.height - window.getHeight()) / 2);
		window.setVisible(true);
		
		window.addWindowListener(new WindowAdapter() {
			@Override public void windowClosing(WindowEvent windowevent) {
				System.out.println("Window closing");
				connection.close();
		}});
//		UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
	}

}
