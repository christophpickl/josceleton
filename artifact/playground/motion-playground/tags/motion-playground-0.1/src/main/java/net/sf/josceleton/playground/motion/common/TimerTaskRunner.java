package net.sf.josceleton.playground.motion.common;

import java.util.TimerTask;

public class TimerTaskRunner extends TimerTask {

	private final Runnable runnable;
	
	public TimerTaskRunner(final Runnable runnable) {
		this.runnable = runnable;
	}

	@Override public final void run() {
		this.runnable.run();
	}

}
