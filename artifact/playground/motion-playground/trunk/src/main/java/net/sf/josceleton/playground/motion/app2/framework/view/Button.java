package net.sf.josceleton.playground.motion.app2.framework.view;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.Toolkit;
import java.awt.image.ImageObserver;
import java.util.Timer;
import java.util.TimerTask;

import net.sf.josceleton.core.impl.async.DefaultAsync;
import net.sf.josceleton.playground.motion.app2.framework.world.WorldSnapshot;

public class Button extends DefaultAsync<ButtonListener> implements Drawable {

	private static final int TIMER_TASK_FINISHED_COUNT = 5;
	private static final int TIMER_TASK_GAP_IN_MS = 500;
	private static final int SIZE = 90;
	
	private final Image image;
	
	private boolean mouseOver;
	private Timer timer;
	private int timerTaskCount;
	private Rectangle area;
	
	public Button(Image image) {
		this.image = image;
	}

	@Override public void drawOnPosition(WorldSnapshot world, Graphics2D g, int x, int y) { // maybe move x/y coordinates into constructor (could set area instance final and hittest would need no null check)
		this.update(world);
		
		if(this.area == null) {
			this.area = new Rectangle(0, 0, image.getWidth(null), image.getHeight(null));
		} else {
			this.area.x = x;
			this.area.y = y;
		}
		
	    g.drawImage(this.image, x, y, null);
	    g.finalize();
	}

	private void update(WorldSnapshot world) {
		// hit test
		if(this.area == null) {
			return;
		}
		
		if(this.area.contains(world.getLocationRHand())) {
			if(this.mouseOver == false) {
				this.mouseOver = true;
				this.startClickDelay();
			} else {
				// wait until delay finished
			}
		} else if(this.mouseOver == true) {
			this.mouseOver = false;
			this.stopClickDelay();
		}
	}

	private void startClickDelay() {
		this.timerTaskCount = 0;
		this.timer = new Timer();
		this.timer.schedule(new TimerTask() {
			@SuppressWarnings("synthetic-access")
			@Override public void run() {
				onNextTimer();
			}
		}, 0, TIMER_TASK_GAP_IN_MS);
	}
	
	private void onNextTimer() {
		this.timerTaskCount++;
		System.out.println("timerTaskCount " + this.timerTaskCount + " of " + TIMER_TASK_FINISHED_COUNT);
		
		if(this.timerTaskCount == TIMER_TASK_FINISHED_COUNT) {
			dispatchClicked();
			this.stopClickDelay();
		}
	}
	
	private void stopClickDelay() {
		this.timerTaskCount = 0;
		if(this.timer != null) {
			this.timer.cancel();
			this.timer = null;
		}
	}
	
	private void dispatchClicked() {
		Toolkit.getDefaultToolkit().beep();
		for(ButtonListener listener : this.getListeners()) {
			listener.onClicked(this);
		}
	}

}
