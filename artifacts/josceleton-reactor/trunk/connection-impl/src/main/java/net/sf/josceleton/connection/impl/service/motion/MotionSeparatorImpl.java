package net.sf.josceleton.connection.impl.service.motion;

import java.util.HashMap;
import java.util.Map;

import net.sf.josceleton.connection.api.Connection;
import net.sf.josceleton.connection.api.service.motion.MotionListener;
import net.sf.josceleton.core.api.entity.Coordinate;
import net.sf.josceleton.core.api.entity.User;
import net.sf.josceleton.core.api.entity.joint.Joint;
import net.sf.josceleton.core.api.entity.message.JointMessage;
import net.sf.josceleton.core.api.entity.message.UserMessage;
import net.sf.josceleton.core.impl.async.DefaultAsyncFor;
import net.sf.josceleton.core.impl.entity.body.SkeletonFactory;
import net.sf.josceleton.core.impl.entity.body.SkeletonInternal;

import com.google.inject.Inject;
import com.google.inject.assistedinject.Assisted;

/**
 * @since 0.4
 */
class MotionSeparatorImpl
	extends DefaultAsyncFor<User, MotionListener>
	implements MotionSeparatorInternal {
	
	private final Connection connection;
	
	private final SkeletonFactory skeletonFactory;
	
	private final Map<User, SkeletonInternal> skeletonByUser = new HashMap<User, SkeletonInternal>();
	
	private boolean isListeningToConnection = false;
	
	private int countAddedListeners = 0;
	
	
	@Inject MotionSeparatorImpl(@Assisted final Connection connection, final SkeletonFactory skeletonFactory) {
		this.connection = connection;
		this.skeletonFactory = skeletonFactory;
	}

	/** {@inheritDoc} from {@link ConnectionListener} */
	@Override public final void onJointMessage(final JointMessage message) {
		final User user = message.getUser();
		final Joint joint = message.getJoint();
		final Coordinate coordinate = message.getCoordinate();
		
		final SkeletonInternal skeleton = this.skeletonByUser.get(user);
		skeleton.update(joint, coordinate);
		
		for (MotionListener currentListener : this.getListenersFor(user)) {
			currentListener.onMoved(joint, coordinate, skeleton);
		}
	}
	
	/** {@inheritDoc} from {@link ConnectionListener} */
	@Override public final void onUserMessage(final UserMessage message) {
		// ignored as we are only interested in motions (joint messages) and select user by our own
	}

	/** {@inheritDoc} from {@link DefaultAsyncFor} */
	@Override protected final void beforeAddListener(final User user, final MotionListener listener) {
		if(this.getListenersFor(user).contains(listener) == false) {
			this.countAddedListeners++;
		}
		
		this.updateListeningToConnection();

		if(this.skeletonByUser.containsKey(user) == false) {
			// the very first one
			final SkeletonInternal newSkeleton = this.skeletonFactory.create();
			this.skeletonByUser.put(user, newSkeleton);
		} 
	}

	/** {@inheritDoc} from {@link DefaultAsyncFor} */
	@Override protected final void beforeRemoveListener(final User user, final MotionListener listener) {
		if(this.getListenersFor(user).contains(listener) == false) {
			this.countAddedListeners--;
		}
		
		this.updateListeningToConnection();
		
		// no if(check if delete skeleton isntance); keep skeleton instance for future use (takes not that much memory)
		// also would need an afterRemoveListener() to remove Skeleton if no one is registered for this user anymore
	}
	
	private void updateListeningToConnection() {
		if(this.isListeningToConnection == false && this.countAddedListeners != 0) {
			this.connection.addListener(this);
			this.isListeningToConnection = true;
		} else if(this.isListeningToConnection == true && this.countAddedListeners == 0) {
			this.connection.removeListener(this);
			this.isListeningToConnection = false;
			this.skeletonByUser.clear(); // wipe out all skeletons! muhahahahaha!!!!!!!!!
		}
	}
	
}
