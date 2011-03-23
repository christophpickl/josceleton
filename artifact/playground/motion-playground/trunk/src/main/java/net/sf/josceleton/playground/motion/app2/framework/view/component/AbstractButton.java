package net.sf.josceleton.playground.motion.app2.framework.view.component;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.Toolkit;
import java.util.Timer;

import net.sf.josceleton.core.impl.async.DefaultAsync;
import net.sf.josceleton.playground.motion.app2.framework.view.resources.Sounds;
import net.sf.josceleton.playground.motion.app2.framework.world.WorldSnapshot;
import net.sf.josceleton.playground.motion.common.TimerTaskRunner;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

abstract class AbstractButton
	extends DefaultAsync<ButtonListener> // TODO maybe extend Rectangle (hat viele checks schon drinnen von wegen hit area intersect etc)
		implements Button, Runnable {

	private static final Log LOG = LogFactory.getLog(AbstractButton.class);
	private static final int TIMER_TASK_FINISHED_COUNT = 6;
	private static final int TIMER_TASK_GAP_IN_MS = 300;

	private static final int INDICATOR_HEIGHT = 6;
	private static final int INDICATOR_WIDTH = 13;
	private static final int INDICATOR_INSET_X = 3;
	private static final int INDICATOR_GAP_Y = 10;
	private static final Color[] INDICATOR_COLORS;
	static {
		INDICATOR_COLORS = new Color[TIMER_TASK_FINISHED_COUNT];
		final Color baseColor = new Color(0x333333);
		Color previousColor = baseColor;
		for (int i = 0; i < TIMER_TASK_FINISHED_COUNT; i++) {
			INDICATOR_COLORS[i] = previousColor.brighter();
			previousColor = INDICATOR_COLORS[i];
		}
	}
	private boolean mouseOver;
	private Timer timer;
	private int timerTaskCount;
	private Rectangle hitArea;
	
	protected abstract Rectangle updateHitArea(Rectangle area, int x, int y, Graphics2D g);
	protected abstract void _drawOnPosition(Graphics2D g, int x, int y, WorldSnapshot world);
	
	// maybe move x/y coordinates into constructor (could set area instance final and hittest would need no null check)
	
	@Override public final void drawOnPosition(Graphics2D g, int x, int y, WorldSnapshot world) {
		this.hitArea = this.updateHitArea(this.hitArea, x, y, g);
		this.checkHitTest(world);
		this._drawOnPosition(g, x, y, world);
		
		// draw time indicator
		if(this.timerTaskCount != 0) {
			for (int i = 0; i < this.timerTaskCount; i++) {
				
				g.setPaint(INDICATOR_COLORS[i]);
				
				if(i == 0) { // middle one
					this.drawLeftRightIndicator(g, i, x, y, 0);
				} else {
					this.drawLeftRightIndicator(g, i, x, y, -1);
					this.drawLeftRightIndicator(g, i, x, y, +1);
				}
			}
		}
	}
	
	private void drawLeftRightIndicator(Graphics2D g, int i, int x, int y, int xByCenterMultiplicator) {
		this.drawTopBottomIndicator(g, i, x, y, xByCenterMultiplicator, true);
		this.drawTopBottomIndicator(g, i, x, y, xByCenterMultiplicator, false);
	}
	
	private void drawTopBottomIndicator(Graphics2D g, int i, int x, int y, int xByCenterMultiplicator, boolean isTop) {
		g.fillRect(
				x + this.hitArea.width / 2 + (i * (INDICATOR_WIDTH + INDICATOR_INSET_X)) * (xByCenterMultiplicator),
				y + (isTop ? (- (INDICATOR_GAP_Y + INDICATOR_HEIGHT)) : (this.hitArea.height + INDICATOR_GAP_Y)),
				INDICATOR_WIDTH, INDICATOR_HEIGHT);
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
		Sounds.BLIP.start();
		
		for(ButtonListener listener : this.getListeners()) {
			listener.onClicked(this);
		}
	}

	@Override public void run() /* aka: onNextTimer()*/ {
		this.timerTaskCount++;
		LOG.trace("timerTaskCount " + this.timerTaskCount + " of " + TIMER_TASK_FINISHED_COUNT);
		
		if(this.timerTaskCount == TIMER_TASK_FINISHED_COUNT) {
			dispatchClicked();
			this.stopClickDelay();
		}
	}
	
}
