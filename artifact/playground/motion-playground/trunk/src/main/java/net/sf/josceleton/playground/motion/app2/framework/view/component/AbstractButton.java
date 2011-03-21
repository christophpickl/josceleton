package net.sf.josceleton.playground.motion.app2.framework.view.component;

import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.Toolkit;
import java.util.Timer;

import javax.management.RuntimeErrorException;

import net.sf.josceleton.core.impl.async.DefaultAsync;
import net.sf.josceleton.playground.motion.app2.framework.world.WorldSnapshot;
import net.sf.josceleton.playground.motion.common.TimerTaskRunner;

abstract class AbstractButton extends DefaultAsync<ButtonListener> implements Button, Runnable {

	private static final int TIMER_TASK_FINISHED_COUNT = 5;
	private static final int TIMER_TASK_GAP_IN_MS = 500;
	
	private final Image image;
	private final String label;
	
	private boolean mouseOver;
	private Timer timer;
	private int timerTaskCount;
	private Rectangle hitArea;

	public AbstractButton(Image image, String label, Dimension size) {
		if(image == null && label == null) throw new RuntimeException("both may not be null");
		if(image != null && label != null) throw new RuntimeException("both may not be nonnull");
		this.image = image;
		this.label = label;
		if(size != null) {
			this.hitArea = new Rectangle(size);
		}
	}
	
	protected abstract Rectangle updateHitArea(Rectangle area, int x, int y);

	protected abstract void _drawOnPosition(Graphics2D g, int x, int y, WorldSnapshot world);
	
	// maybe move x/y coordinates into constructor (could set area instance final and hittest would need no null check)
	@Override public final void drawOnPosition(Graphics2D g, int x, int y, WorldSnapshot world) {
		this.hitArea = this.updateHitArea(this.hitArea, x, y);
		this.checkHitTest(world);
		this._drawOnPosition(g, x, y, world);
	}
	
	private final void checkHitTest(WorldSnapshot world) {
		// hit test
		if(this.hitArea == null) {
			return;
		}
		
		if(this.hitArea.contains(world.getCursorLocation())) {
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
		this.timer.schedule(new TimerTaskRunner(this), 0, TIMER_TASK_GAP_IN_MS);
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

	@Override public void run() /* aka: onNextTimer()*/ {
		this.timerTaskCount++;
		System.out.println("timerTaskCount " + this.timerTaskCount + " of " + TIMER_TASK_FINISHED_COUNT);
		
		if(this.timerTaskCount == TIMER_TASK_FINISHED_COUNT) {
			dispatchClicked();
			this.stopClickDelay();
		}
	}
	
	protected final String getLabel() {
		if(this.label == null) {
			throw new RuntimeException("label == null");
		}
		return this.label;
	}
	
	protected final Image getImage() {
		if(this.image == null) {
			throw new RuntimeException("image == null");
		}
		return this.image;
	}
	
}
