package net.sf.josceleton.connection.impl.service.motion;

import net.sf.josceleton.connection.api.Connection;
import net.sf.josceleton.connection.api.service.motion.ContinuousMotionStream;
import net.sf.josceleton.connection.api.service.motion.MotionStream;
import net.sf.josceleton.connection.api.service.motion.MotionStreamFactory;
import net.sf.josceleton.connection.api.service.user.UserService;

import com.google.inject.Inject;

class ContinuousMotionStreamFactoryImpl implements
		ContinuousMotionStreamFactory {
	
	private final ContinuousMotionStreamInternalFactory internalStreamFactory;
	
	private final MotionStreamFactory streamFactory;
	
	@Inject ContinuousMotionStreamFactoryImpl(
			final ContinuousMotionStreamInternalFactory internalStreamFactory,
			final MotionStreamFactory streamFactory) {
		this.internalStreamFactory = internalStreamFactory;
		this.streamFactory = streamFactory;
	}
	
	@Override public final ContinuousMotionStream create(final Connection connection) {
		final MotionStream stream = this.streamFactory.create(connection);
		final UserService userService = connection.getUserService();
		final ContinuousMotionStreamInternal continuousStream =
			this.internalStreamFactory.create(stream, userService);
		
		continuousStream.initAttachingToUser();
		userService.addListener(continuousStream); // FIXME somewhen, someone has to remove listener!!!
		return continuousStream;
	}

}
