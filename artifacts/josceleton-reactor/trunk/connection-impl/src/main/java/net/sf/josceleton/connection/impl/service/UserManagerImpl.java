package net.sf.josceleton.connection.impl.service;

import java.util.HashMap;
import java.util.Map;

import net.sf.josceleton.connection.api.service.UserManagerListener;
import net.sf.josceleton.core.api.entity.User;
import net.sf.josceleton.core.api.entity.UserState;
import net.sf.josceleton.core.impl.async.AsyncDelegator;
import net.sf.josceleton.core.impl.entity.UserFactory;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.google.inject.Inject;

/**
 * @since 0.3
 */
class UserManagerImpl
	extends AsyncDelegator<UserManagerListener>
	implements UserManagerInternal /* == { UserManager, UserStore } */ {
	
	private static final Log LOG = LogFactory.getLog(UserManagerImpl.class);
	
	private final UserFactory factory;
	
	private final Map<Integer, User> usersById = new HashMap<Integer, User>();

//	private final Collection<User> waitingUsers = new HashSet<User>();
//	
//	private final Collection<User> availableUsers = new HashSet<User>();
	
	@Inject UserManagerImpl(final UserFactory factory) {
		this.factory = factory;
	}

	@Override public final User lookupUserForJointMessage(final Integer osceletonUserId) {
		final User storedUser = this.usersById.get(osceletonUserId);
		if(storedUser != null) {
			return storedUser;
		}
		
		return null; // FIXME
	}

	@Override public final User lookupUserForUserMessage(final Integer osceletonUserId, final UserState userState) {
		if(userState == UserState.WAITING) {
			if(this.usersById.containsKey(osceletonUserId) == true) {
				throw new IllegalStateException("Already add new user with ID [" + osceletonUserId + "]! " +
						"Registered user is: " + this.usersById.get(osceletonUserId));
			}
			
			final User newUser = this.createWaitingUser(osceletonUserId);
			this.dispatchWaitingUser(newUser);
			return newUser;
			
		} else if(userState == UserState.PROCESSING) {
			final User storedUser = this.usersById.get(osceletonUserId);
			final User processingUser;
			if(storedUser != null) {
				processingUser = storedUser;
			} else {
				processingUser = this.createWaitingUser(osceletonUserId);
				this.dispatchWaitingUser(processingUser);
			}
			
			this.dispatchProcessingUser(processingUser);
			return processingUser;
			
		} else if(userState == UserState.DEAD) {
			final User removedUser = this.usersById.remove(osceletonUserId);
			if(removedUser == null) {
				LOG.warn("Received lost message for user with ID [" + osceletonUserId + "], " +
						"though user was not yet registered!");
			}
			this.dispatchDeadUser(removedUser);
			return removedUser;
			
		} else {
			throw new RuntimeException("Unhandled user state [" + userState + "]!");
		}
	}
	
	private User createWaitingUser(final Integer osceletonUserId) {
		final int xxx = 42; // FIXME create proper unique id
		return this.factory.create(xxx, osceletonUserId.intValue());
	}
	
	
	private void dispatchWaitingUser(final User user) {
		for (final UserManagerListener listener : this.getListeners()) {
			listener.onUserWaiting(user);
		}
	}
	
	private void dispatchProcessingUser(final User user) {
		for (final UserManagerListener listener : this.getListeners()) {
			listener.onUserProcessing(user);
		}
	}
	
	private void dispatchDeadUser(final User user) {
		for (final UserManagerListener listener : this.getListeners()) {
			listener.onUserDead(user);
		}
	}
	
}
