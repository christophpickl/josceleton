package net.sf.josceleton.connection.impl.service;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;

import javax.management.RuntimeErrorException;

import net.sf.josceleton.connection.api.service.UserService;
import net.sf.josceleton.connection.api.service.UserServiceListener;
import net.sf.josceleton.core.api.entity.User;
import net.sf.josceleton.core.api.entity.UserState;
import net.sf.josceleton.core.api.entity.UserStateFunction;
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

	// LUXURY @CODE DESIGN outsource getting current snapshot of all users to other class!!!
	private final Collection<User> waitingUsers = new LinkedList<User>();
	
	private final Collection<User> processingUsers = new LinkedList<User>();
	
	
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
	@SuppressWarnings("synthetic-access")
	@Override public final User lookupUserForUserMessage(final Integer osceletonUserId, final UserState userState) {
		return userState.callback(new UserStateFunction<User>() {
			@Override public User onStateWaiting() {
				return lookupWaitingUser(osceletonUserId);
			}
			@Override public User onStateProcessing() {
				return lookupProcessingUser(osceletonUserId);
			}
			@Override public User onStateDead() {
				return lookupDeadUser(osceletonUserId);
			}
		});
	}
	
	private User lookupDeadUser(final Integer osceletonUserId) {
		final User removedStoredUser = this.usersById.remove(osceletonUserId);
		
		final User userToDispatch;
		if(removedStoredUser != null) {
			// simple case: user was already known to josceleton (was either waiting or processing)
			userToDispatch = removedStoredUser;
			
		} else {
			// this is an EXPECTED state; kinect detected user before josceleton started listening, but lost again.
			// artificially invoke one state step before
			userToDispatch = this.lookupProcessingUser(osceletonUserId);
		}
		
		// TODO @CODE DRY refactor removing from user lists (is it possible to outsource this logic? remove getters...)
		
		// update processing users
		if(removedStoredUser == null) {
			// was artificially created, so has to be that way
			if(this.processingUsers.remove(userToDispatch) == false) {
				throw new RuntimeException("Could not remove " + userToDispatch + " from processing users: " +
						Arrays.toString(this.processingUsers.toArray()));
			}
			
		} else { // 
			if(this.waitingUsers.contains(removedStoredUser) == true) {
				if(this.waitingUsers.remove(removedStoredUser) == false) {
					throw new RuntimeException("Could not remove " + removedStoredUser + " from waiting users: " +
							Arrays.toString(this.waitingUsers.toArray()));
				}
			} else {
				if(this.processingUsers.remove(removedStoredUser) == false) {
					throw new RuntimeException("Could not remove " + removedStoredUser + " from processing users: " +
							Arrays.toString(this.processingUsers.toArray()));
				}
			}
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
		
		// update waiting and processing users
		if(this.waitingUsers.remove(processingUser) == false) {
			throw new RuntimeException("Could not remove " + processingUser + " from waiting users: " +
				Arrays.toString(this.waitingUsers.toArray()));
		}
		if(this.processingUsers.add(processingUser) == false) {
			throw new RuntimeException("Already added " + processingUser + " from processing users: " +
					Arrays.toString(this.processingUsers.toArray()));
		}
		
		// dispatchProcessingUser(processingUser);
		for (final UserServiceListener listener : this.getListeners()) {
			listener.onUserProcessing(processingUser);
		}
		
		return processingUser;
	}

	private User lookupWaitingUser(final Integer osceletonUserId) {
		if(this.usersById.containsKey(osceletonUserId) == true) {
			// FIXME somehow make it possible for users to catch such errors
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
		// update waiting users
		if(this.waitingUsers.add(newUser) == false) {
			throw new RuntimeException("Already added " + newUser + " from waiting users: " +
					Arrays.toString(this.waitingUsers.toArray()));
		}
		
		return newUser;
	}

	private void dispatchWaitingUser(final User user) {
		for (final UserServiceListener listener : this.getListeners()) {
			listener.onUserWaiting(user);
		}
	}

	/** {@inheritDoc} from {@link UserService} */
	@Override public final Collection<User> getProcessingUsers() {
		return Collections.unmodifiableCollection(this.processingUsers);
	}

	/** {@inheritDoc} from {@link UserService} */
	@Override public final Collection<User> getWaitingUsers() {
		return Collections.unmodifiableCollection(this.waitingUsers);
	}
	
}
