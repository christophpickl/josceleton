package net.sf.josceleton.connection.impl.service.motion;

import net.sf.josceleton.connection.api.Connection;
import net.sf.josceleton.connection.api.service.motion.MotionSeparatorListener;
import net.sf.josceleton.connection.api.service.motion.Skeleton;
import net.sf.josceleton.core.api.entity.User;
import net.sf.josceleton.core.api.entity.message.JointMessage;
import net.sf.josceleton.core.api.entity.message.UserMessage;
import net.sf.josceleton.core.impl.async.DefaultAsyncFor;

import com.google.inject.Inject;
import com.google.inject.assistedinject.Assisted;

/**
 * @since 0.4
 */
class MotionSeparatorImpl
	extends DefaultAsyncFor<User, MotionSeparatorListener>
	implements MotionSeparatorInternal {
	
	private final Connection connection;
	
	private boolean isListeningToConnection = false;
	
	private int countAddedListeners = 0;
	
	
	@Inject MotionSeparatorImpl(@Assisted final Connection connection) {
		this.connection = connection;
	}
	
	@Override public final void onJointMessage(final JointMessage message) {
		final User user = message.getUser();
		final Skeleton skeleton = null; // FIXME skeleton
		for (MotionSeparatorListener currentListener : this.getListenersFor(user)) {
			currentListener.onMoved(message.getJointPart(), message.getCoordinate(), skeleton);
		}
	}

	@Override public final void onUserMessage(final UserMessage message) {
		// ignored as we are only interested in motions (joint messages) and select user by our own
	}

	@Override protected final void beforeAddListener(final User user, final MotionSeparatorListener listener) {
		if(this.getListenersFor(user).contains(listener) == false) {
			this.countAddedListeners++;
		}
		this.updateListeningToConnection();
	}

	@Override protected final void beforeRemoveListener(final User user, final MotionSeparatorListener listener) {
		if(this.getListenersFor(user).contains(listener) == false) {
			this.countAddedListeners--;
		}
		this.updateListeningToConnection();
	}
	
	private void updateListeningToConnection() {
		if(this.isListeningToConnection == false && this.countAddedListeners != 0) {
			this.connection.addListener(this);
			this.isListeningToConnection = true;
		}
		
		if(this.isListeningToConnection == true && this.countAddedListeners == 0) {
			this.connection.removeListener(this);
			this.isListeningToConnection = false;
		}
	}
	
}
