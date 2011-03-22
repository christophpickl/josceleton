package net.sf.josceleton.playground.motion.app2.framework.motionx;

import net.sf.josceleton.core.impl.async.DefaultAsync;

public abstract class AbstractPosition extends DefaultAsync<PositionListener> implements Position {
	
	protected final void dispatchPositionDetected() {
		for(final PositionListener listener : this.getListeners()) {
			listener.onPositionDetected();
		}
	}
	
}
