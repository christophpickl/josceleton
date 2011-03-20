package net.sf.josceleton.connection.impl.service.motion;

import java.util.Collection;

import net.sf.josceleton.connection.api.service.motion.MotionSupplier;
import net.sf.josceleton.connection.api.service.motion.MotionSupplierListener;
import net.sf.josceleton.connection.api.service.user.UsersCollection;
import net.sf.josceleton.connection.api.service.user.UserServiceListener;
import net.sf.josceleton.core.api.entity.joint.Joint;
import net.sf.josceleton.core.api.entity.joint.Skeleton;
import net.sf.josceleton.core.api.entity.location.Coordinate;
import net.sf.josceleton.core.api.entity.user.User;
import net.sf.josceleton.core.impl.async.DefaultAsync;

import com.google.inject.Inject;
import com.google.inject.assistedinject.Assisted;

/**
 * @since 0.5
 */
class ContinuousMotionSupplierInternalImpl
	extends DefaultAsync<MotionSupplierListener>
		implements ContinuousMotionSupplierInternal {
	
	private final MotionSupplier internalSupplier;
	
	private final UsersCollection users;
	
	private User currentlyListeningTo = null;

	
	@Inject ContinuousMotionSupplierInternalImpl(
			@Assisted final MotionSupplier internalSupplier,
			@Assisted final UsersCollection users) {
		this.internalSupplier = internalSupplier;
		this.users = users;
	}

	/** {@inheritDoc} from {@link ContinuousMotionSupplierInternal} */
	@Override public final void initAttachingToUser() {
		final Collection<User> processingUsers = this.users.getProcessing();
		if(processingUsers.isEmpty() == false) {
			// return next == first == oldest user to reattach as our next victim, wuhahaha ;)
			this.startListeningTo(processingUsers.iterator().next());
		}
	}
	
	/** {@inheritDoc} from {@link UserServiceListener} */
	@Override public final void onUserWaiting(final User user) {
		// ignore; we are not interested in waiting users
	}

	/** {@inheritDoc} from {@link UserServiceListener} */
	@Override public final void onUserProcessing(final User user) {
		if(this.currentlyListeningTo == null) {
			this.startListeningTo(user);
		}
	}

	/** {@inheritDoc} from {@link UserServiceListener} */
	@Override public final void onUserDead(final User user) {
		if(this.currentlyListeningTo != null && this.currentlyListeningTo.equals(user)) {
			this.stopListeningTo();
		}
	}

	/** {@inheritDoc} from {@link MotionSupplierListener} */
	@Override
	public final void onMoved(final Joint movedJoint, final Coordinate updatedCoordinate, final Skeleton skeleton) {
		// just redispatch
		for(final MotionSupplierListener listener : this.getListeners()) {
			listener.onMoved(movedJoint, updatedCoordinate, skeleton);
		}
	}
	
	private void startListeningTo(final User user) {
		this.currentlyListeningTo = user;
		this.internalSupplier.addListenerFor(this.currentlyListeningTo, this);
	}
	
	private void stopListeningTo() {
		this.internalSupplier.removeListenerFor(this.currentlyListeningTo, this);
		this.currentlyListeningTo = null;
		
		this.initAttachingToUser(); // retry
	}

}
