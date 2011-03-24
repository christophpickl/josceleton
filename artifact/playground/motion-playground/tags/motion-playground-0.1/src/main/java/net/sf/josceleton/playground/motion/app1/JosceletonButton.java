/**
 * 
 */
package net.sf.josceleton.playground.motion.app1;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Point;
import java.awt.Rectangle;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;
import java.util.Timer;
import java.util.TimerTask;

import net.sf.josceleton.core.api.async.Async;
import net.sf.josceleton.playground.motion.app1.ContentPanel.ViewComponentListener;

class JosceletonButton extends Rectangle implements Async<ViewComponentListener>{
	private static final long serialVersionUID = 1406257574775722027L;
	private static final Color BASE_OVER_COLOR = Color.GREEN;
	private static final int TRIGGER_CLICK_COUNT = 5;
	
	private final Set<ViewComponentListener> listeners = new HashSet<ViewComponentListener>();
	private boolean mouseOver;
	private Timer timer;
	private int clickCount;
	
	public JosceletonButton(Point point, Dimension dimension) {
		super(point, dimension);
	}
	
	public Color getColor() {
		if(this.mouseOver == true) {
			Color c = BASE_OVER_COLOR;
			for (int i = 0; i < this.clickCount; i++) {
				c = c.brighter();
			}
			return c;
		}
		return Color.GRAY;
	}
	
	public void makeHitTest(final Point point) {
		if(this.contains(point) == true) {
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
		this.clickCount = 0;
		this.timer = new Timer();
		this.timer.schedule(new TimerTask() {
			@SuppressWarnings("synthetic-access")
			@Override public void run() {
				onNextTimer();
			}
		}, 0, 500);
	}
	
	private void onNextTimer() {
		this.clickCount++;
		System.out.println("clickCount " + clickCount + " of " + TRIGGER_CLICK_COUNT);
		
		if(this.clickCount == TRIGGER_CLICK_COUNT) {
			dispatchClicked();
			this.stopClickDelay();
		}
	}
	
	private void stopClickDelay() {
		this.clickCount = 0;
		if(this.timer != null) {
			this.timer.cancel();
			this.timer = null;
		}
	}
	
	private void dispatchClicked() {
		for(ViewComponentListener listener : this.listeners) {
			listener.onClicked(this);
		}
	}
	
	@Override public void addListener(ViewComponentListener listener) {
		this.listeners.add(listener);
	}

	@Override public void removeListener(ViewComponentListener listener) {
		this.listeners.remove(listener);
	}
	protected final Collection<ViewComponentListener> getListeners() {
		return this.listeners;
	}
}