package net.sf.josceleton.experimental;

import net.sf.josceleton.Josceleton;
import net.sf.josceleton.connection.api.Connection;
import net.sf.josceleton.connection.api.service.motion.ContinuousMotionStream;
import net.sf.josceleton.connection.api.service.motion.MotionStreamListener;
import net.sf.josceleton.connection.impl.service.motion.ContinuousMotionStreamFactory;

public class JosceletonContinuousMotionStream implements ContinuousMotionStream {
	
	private static final ContinuousMotionStreamFactory FACTORY = Josceleton.getContinuousMotionStreamFactory();
	
	private final ContinuousMotionStream delegate;
	
	
	public JosceletonContinuousMotionStream(final Connection connection) {
		this.delegate = FACTORY.create(connection);
	}

	@Override public final void addListener(final MotionStreamListener listener) {
		this.delegate.addListener(listener);
	}

	@Override public final void removeListener(final MotionStreamListener listener) {
		this.delegate.removeListener(listener);
	}

}
