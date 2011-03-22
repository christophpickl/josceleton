package net.sf.josceleton.playground.motion.app2.framework.view.component;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.Toolkit;
import java.util.Timer;

import net.sf.josceleton.core.impl.async.DefaultAsync;
import net.sf.josceleton.playground.motion.app2.framework.world.WorldSnapshot;
import net.sf.josceleton.playground.motion.common.TimerTaskRunner;

abstract class AbstractButton
	extends DefaultAsync<ButtonListener> // TODO maybe extend Rectangle (hat viele checks schon drinnen von wegen hit area intersect etc)
		implements Button, Runnable {

	private static final int TIMER_TASK_FINISHED_COUNT = 6;
	private static final int TIMER_TASK_GAP_IN_MS = 300;
	
	private boolean mouseOver;
	private Timer timer;
	private int timerTaskCount;
	private Rectangle hitArea;
	

	protected abstract Rectangle updateHitArea(Rectangle area, int x, int y, Graphics2D g);

	protected abstract void _drawOnPosition(Graphics2D g, int x, int y, WorldSnapshot world);
	
	private static final int INDICATOR_Y_GAP = 5;
	private static final int INDICATOR_HEIGHT = 3;
	private static final int INDICATOR_WIDTH = 9;
	// maybe move x/y coordinates into constructor (could set area instance final and hittest would need no null check)
	@Override public final void drawOnPosition(Graphics2D g, int x, int y, WorldSnapshot world) {
		this.hitArea = this.updateHitArea(this.hitArea, x, y, g);
		this.checkHitTest(world);
		this._drawOnPosition(g, x, y, world);
		
		// draw time indicator
		if(this.timerTaskCount != 0) {
			System.out.println("drawing indicators: " + this.timerTaskCount);
			for (int i = 0; i < this.timerTaskCount; i++) {
				g.setPaint(Color.LIGHT_GRAY);
				
				if(i == 0) {
					g.fillRect(x + this.hitArea.width / 2,            y + this.hitArea.height + INDICATOR_Y_GAP, INDICATOR_WIDTH, INDICATOR_HEIGHT);
				} else {
					g.fillRect(x + this.hitArea.width / 2 - (i * 10), y + this.hitArea.height + INDICATOR_Y_GAP, INDICATOR_WIDTH, INDICATOR_HEIGHT);
					g.fillRect(x + this.hitArea.width / 2 + (i * 10), y + this.hitArea.height + INDICATOR_Y_GAP, INDICATOR_WIDTH, INDICATOR_HEIGHT);
				}
			}
		}
	}
	
	
	private final void checkHitTest(WorldSnapshot world) {
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
	
}
