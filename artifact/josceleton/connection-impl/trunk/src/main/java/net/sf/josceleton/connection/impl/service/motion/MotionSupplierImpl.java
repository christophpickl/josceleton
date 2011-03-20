package net.sf.josceleton.connection.impl.service.motion;

import java.util.HashMap;
import java.util.Map;

import net.sf.josceleton.connection.api.Connection;
import net.sf.josceleton.connection.api.service.motion.MotionSupplierListener;
import net.sf.josceleton.core.api.entity.joint.Joint;
import net.sf.josceleton.core.api.entity.location.Coordinate;
import net.sf.josceleton.core.api.entity.message.JointMessage;
import net.sf.josceleton.core.api.entity.message.UserMessage;
import net.sf.josceleton.core.api.entity.user.User;
import net.sf.josceleton.core.impl.async.DefaultAsyncFor;
import net.sf.josceleton.core.impl.entity.joint.SkeletonFactory;
import net.sf.josceleton.core.impl.entity.joint.SkeletonInternal;

import com.google.inject.Inject;
import com.google.inject.assistedinject.Assisted;

/**
 * @since 0.4
 */
class MotionSupplierImpl
	extends DefaultAsyncFor<User, MotionSupplierListener>
	implements MotionSupplierInternal {
	
	private final Connection connection;
	
	private final SkeletonFactory skeletonFactory;
	
	private final Map<User, SkeletonInternal> skeletonByUser = new HashMap<User, SkeletonInternal>();
	
	private int countAddedListeners = 0;
	
	
	@Inject MotionSupplierImpl(@Assisted final Connection connection, final SkeletonFactory skeletonFactory) {
		this.connection = connection;
		this.skeletonFactory = skeletonFactory;
	}

	/** {@inheritDoc} from {@link ConnectionListener} */
	@Override public final void onJointMessage(final JointMessage message) {
		final User msgUser = message.getUser();
		if(this.skeletonByUser.containsKey(msgUser) == false) {
			// no one is interested in joint messages for this user; see #beforeAddListener(..)
			return;
		}
		
		final Joint msgJoint = message.getJoint();
		final Coordinate msgCoordinate = message.getCoordinate();
		
		final SkeletonInternal skeleton = this.skeletonByUser.get(msgUser);
		skeleton.update(msgJoint, msgCoordinate);
		
		for (MotionSupplierListener currentListener : this.getListenersFor(msgUser)) {
			currentListener.onMoved(msgJoint, msgCoordinate, skeleton);
		}
	}
	
	/** {@inheritDoc} from {@link ConnectionListener} */
	@Override public final void onUserMessage(final UserMessage message) {
		// ignored as we are only interested in motions (joint messages) and select user by our own
	}

	/** {@inheritDoc} from {@link DefaultAsyncFor} */
	@Override protected final void beforeAddListener(final User user, final MotionSupplierListener listener) {
		if(this.getListenersFor(user).contains(listener) == false) {
			this.countAddedListeners++;
			
			if(this.countAddedListeners == 1) {
				this.connection.addListener(this);
			}
		}

		if(this.skeletonByUser.containsKey(user) == false) {
			// the very first one
			final SkeletonInternal newSkeleton = this.skeletonFactory.create();
			this.skeletonByUser.put(user, newSkeleton);
		} 
	}

	/** {@inheritDoc} from {@link DefaultAsyncFor} */
	@Override protected final void beforeRemoveListener(final User user, final MotionSupplierListener listener) {
		if(this.getListenersFor(user).contains(listener) == true) {
			this.countAddedListeners--;
			
			if(this.countAddedListeners == 0) {
				this.connection.removeListener(this);
				this.skeletonByUser.clear(); // wipe out all skeletons! muhahahahaha!!!!!!!!!
			}
		}
		
		
		// NO check if delete specific skeleton instance; keep it for future use (takes not that much memory)
		// also would need an afterRemoveListener() to remove Skeleton if no one is registered for this user anymore
	}
	
	
}
