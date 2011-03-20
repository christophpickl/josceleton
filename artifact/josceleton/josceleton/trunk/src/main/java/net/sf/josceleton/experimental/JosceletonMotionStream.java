package net.sf.josceleton.experimental;

import net.sf.josceleton.Josceleton;
import net.sf.josceleton.connection.api.Connection;
import net.sf.josceleton.connection.api.service.motion.MotionStream;
import net.sf.josceleton.connection.api.service.motion.MotionStreamFactory;
import net.sf.josceleton.connection.api.service.motion.MotionStreamListener;
import net.sf.josceleton.core.api.entity.user.User;

public class JosceletonMotionStream implements MotionStream {
	
	private static final MotionStreamFactory FACTORY = Josceleton.getMotionStreamFactory();
	
	private final MotionStream delegate;
	
	
	public JosceletonMotionStream(final Connection connection) {
		this.delegate = FACTORY.create(connection);
	}

	@Override public final void addListenerFor(final User key, final MotionStreamListener listener) {
		this.delegate.addListenerFor(key, listener);
	}

	@Override public final void removeListenerFor(final User key, final MotionStreamListener listener) {
		this.delegate.removeListenerFor(key, listener);
	}

}
