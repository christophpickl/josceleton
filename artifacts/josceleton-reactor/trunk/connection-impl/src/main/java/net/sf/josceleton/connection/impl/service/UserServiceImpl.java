package net.sf.josceleton.connection.impl.service;

import java.util.HashMap;
import java.util.Map;

import net.sf.josceleton.connection.api.service.UserServiceListener;
import net.sf.josceleton.core.api.entity.User;
import net.sf.josceleton.core.api.entity.UserState;
import net.sf.josceleton.core.impl.async.AsyncDelegator;
import net.sf.josceleton.core.impl.entity.UserFactory;

import com.google.inject.Inject;

/**
 * @since 0.3
 */
class UserServiceImpl
	extends AsyncDelegator<UserServiceListener>
	implements UserServiceInternal /* == { UserService, UserStore } */ {
	
	private final UserFactory factory;
	
	private final Map<Integer, User> usersById = new HashMap<Integer, User>();

//	private final Collection<User> waitingUsers = new HashSet<User>();
//	
//	private final Collection<User> availableUsers = new HashSet<User>();
	
	
	@Inject UserServiceImpl(final UserFactory factory) {
		this.factory = factory;
	}

	/** {@inheritDoc} from {@link UserStore} */
	@Override public final User lookupUserForJointMessage(final Integer osceletonUserId) {
		final User storedUser = this.usersById.get(osceletonUserId);
		if(storedUser != null) {
			return storedUser;
		}

		// artificially invoke one state step before; this is a common case.
		final User artificalUser = this.lookupProcessingUser(osceletonUserId);
		return artificalUser;
	}

	/** {@inheritDoc} from {@link UserStore} */
	@Override public final User lookupUserForUserMessage(final Integer osceletonUserId, final UserState userState) {
		if(userState == UserState.WAITING) {
			return lookupWaitingUser(osceletonUserId);
			
		} else if(userState == UserState.PROCESSING) {
			return lookupProcessingUser(osceletonUserId);
			
		} else if(userState == UserState.DEAD) {
			return lookupDeadUser(osceletonUserId);
			
		} else { // TODO @CODE DESIGN if-else cascade for UserState enum (use callback interface instead, as used previously)
			throw new RuntimeException("Unhandled user state [" + userState + "]!");
		}
	}

	private User lookupDeadUser(final Integer osceletonUserId) {
		final User removedUser = this.usersById.remove(osceletonUserId);
		
		final User userToDispatch;
		if(removedUser != null) {
			userToDispatch = removedUser;
		} else {
			// this is an EXPECTED state; kinect detected user before josceleton started listening, but lost again.
//			LOG.warn("Received lost message for user with ID [" + osceletonUserId + "], " +
//				"though user was not yet registered!");
			
			// artificially invoke one state step before
			userToDispatch = this.lookupProcessingUser(osceletonUserId);
		}
		
		// dispatchDeadUser(dispatchingUser);
		for (final UserServiceListener listener : this.getListeners()) {
			listener.onUserDead(userToDispatch);
		}
		
		return userToDispatch;
	}

	private User lookupProcessingUser(final Integer osceletonUserId) {
		final User storedUser = this.usersById.get(osceletonUserId);
		final User processingUser;
		
		if(storedUser != null) {
			processingUser = storedUser;
		} else {
			// artificially invoke one state step before
			processingUser = this.newWaitingUser(osceletonUserId);
			this.dispatchWaitingUser(processingUser);
		}
		
		// dispatchProcessingUser(processingUser);
		for (final UserServiceListener listener : this.getListeners()) {
			listener.onUserProcessing(processingUser);
		}
		
		return processingUser;
	}

	private User lookupWaitingUser(final Integer osceletonUserId) {
		if(this.usersById.containsKey(osceletonUserId) == true) {
			throw new IllegalStateException("Already add new user with ID [" + osceletonUserId + "]! " +
					"Registered user is: " + this.usersById.get(osceletonUserId));
		}
		
		final User newUser = this.newWaitingUser(osceletonUserId);
		this.dispatchWaitingUser(newUser);
		return newUser;
	}
	
	private User newWaitingUser(final Integer osceletonUserId) {
		final User newUser = this.factory.create(osceletonUserId.intValue());
		this.usersById.put(osceletonUserId, newUser);
		return newUser;
	}
	
	
	private void dispatchWaitingUser(final User user) {
		for (final UserServiceListener listener : this.getListeners()) {
			listener.onUserWaiting(user);
		}
	}
	
}
