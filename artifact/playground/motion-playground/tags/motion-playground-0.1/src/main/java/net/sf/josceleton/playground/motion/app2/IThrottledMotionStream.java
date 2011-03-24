package net.sf.josceleton.playground.motion.app2;

import net.sf.josceleton.connection.api.service.motion.MotionStreamListener;

public interface IThrottledMotionStream {

	void addListener(MotionStreamListener listener);

	void requestForRemove(MotionStreamListener listener);

}
