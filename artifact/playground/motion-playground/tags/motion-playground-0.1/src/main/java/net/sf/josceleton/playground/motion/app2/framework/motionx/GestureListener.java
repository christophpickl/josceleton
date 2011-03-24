package net.sf.josceleton.playground.motion.app2.framework.motionx;

import net.sf.josceleton.core.api.async.Listener;

public interface GestureListener<R extends GestureResult> extends Listener {

	void onGestureDetected(R resutl);

}
