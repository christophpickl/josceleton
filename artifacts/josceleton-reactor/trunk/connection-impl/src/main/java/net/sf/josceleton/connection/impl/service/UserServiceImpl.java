package net.sf.josceleton.connection.impl.service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;

import net.sf.josceleton.commons.collection.SafeRemoveAddList;
import net.sf.josceleton.connection.api.service.UserService;
import net.sf.josceleton.connection.api.service.UserServiceListener;
import net.sf.josceleton.core.api.entity.User;
import net.sf.josceleton.core.api.entity.UserState;
import net.sf.josceleton.core.api.entity.UserStateFunction;
import net.sf.josceleton.core.impl.async.AsyncDelegator;
import net.sf.josceleton.core.impl.entity.UserFactory;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.google.inject.Inject;

/**
 * @since 0.3
 */
class UserServiceImpl
	extends AsyncDelegator<UserServiceListener>
	implements UserServiceInternal /* == { UserService, UserStore } */ {
	
	// FIXME @CODE REFACTOR UserServiceImpl ~300 lines!
	
	private static final Log LOG = LogFactory.getLog(UserServiceImpl.class);
	
	private final UserFactory factory;
	
	private final Map<Integer, User> usersById = new HashMap<Integer, User>();

	// LUXURY @CODE DESIGN outsource getting current snapshot of all users to other class!!!
	private final Collection<User> waitingUsers = new SafeRemoveAddList<User>();
	
	private final Collection<User> processingUsers = new SafeRemoveAddList<User>();
	
	
	@Inject UserServiceImpl(final UserFactory factory) {
		this.factory = factory;
	}

	/** {@inheritDoc} from {@link UserStore} */
	@Override public final User lookupUserForJointMessage(final Integer osceletonUserId) {
//		LOG.info("+lookupUserForJointMessage(osceletonUserId=" + osceletonUserId + ")");
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
		LOG.info("+lookupUserForUserMessage(osceletonUserId=" + osceletonUserId + ", userState=" + userState + ")");
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
		LOG.debug("-lookupDeadUser(osceletonUserId=" + osceletonUserId + ")");
		final User removedStoredUser = this.usersById.remove(osceletonUserId);
		final boolean wasPreviouslyStored = removedStoredUser != null;
		
		final User userToDispatch;
		if(wasPreviouslyStored == true) {
			// simple case: user was already known to josceleton (was either waiting or processing)
			userToDispatch = removedStoredUser;
			
		} else {
			// this is an EXPECTED state; kinect detected user before josceleton started listening, but lost again.
			// artificially invoke one state step before
			userToDispatch = this.lookupProcessingUser(osceletonUserId);
		}
		
		// TODO @CODE DRY refactor removing from user lists (is it possible to outsource this logic? remove getters...)
		
		// was not yet stored, therefore definetely update in processing users
		if(wasPreviouslyStored == false) {
			// was artificially created, so has to be that way
			this.processingUsers.remove(userToDispatch);
			
		} else { // user was either stored as waiting or processing
			if(this.waitingUsers.contains(removedStoredUser) == true) {
				this.waitingUsers.remove(removedStoredUser);
			} else {
				this.processingUsers.remove(removedStoredUser);
			}
		}
		
		for (final UserServiceListener listener : this.getListeners()) {
			listener.onUserDead(userToDispatch);
		}
		
		return userToDispatch;
	}

	private User lookupProcessingUser(final Integer osceletonUserId) {
		LOG.debug("-lookupProcessingUser(osceletonUserId=" + osceletonUserId + ")");
		final User storedUser = this.usersById.get(osceletonUserId);
		final boolean wasPreviouslyStored = storedUser != null;
		final User processingUser;
		
		if(wasPreviouslyStored == true) {
			processingUser = storedUser;
		} else { // artificially invoke one state step before
			processingUser = this.newWaitingUser(osceletonUserId);
			this.dispatchWaitingUser(processingUser);
		}
		
		// update waiting and processing users
		this.waitingUsers.remove(processingUser);
		this.processingUsers.add(processingUser);
		
		// dispatchProcessingUser(processingUser);
		for (final UserServiceListener listener : this.getListeners()) {
			listener.onUserProcessing(processingUser);
		}
		
		return processingUser;
	}

	private User lookupWaitingUser(final Integer osceletonUserId) {
		LOG.debug("-lookupWaitingUser(osceletonUserId=" + osceletonUserId + ")");
		if(this.usersById.containsKey(osceletonUserId) == true) {
			this.fallback();
		}
		
		final User newUser = this.newWaitingUser(osceletonUserId);
		this.dispatchWaitingUser(newUser);
		return newUser;
	}
	
	private void fallback() {
		LOG.error("-fallback()");
		System.err.println("FALLBACK, juchu! (nachher die meldung wieder rausgeben, ja!?)"); // FIXME remove sysout
//		new Exception().printStackTrace();
		
		this.fallbackClear(this.processingUsers);
		this.fallbackClear(this.waitingUsers);
		assert this.usersById.isEmpty() : "usersById should be empty after fallback!";
	}
	
	/** Artificially drop out users (will dispatch events to listeners). */
	private void fallbackClear(final Collection<User> usersToBeEmptied) {
		LOG.trace("-fallbackClear(usersToBeEmptied)");
		final Collection<User> usersToBeEmptiedCopy = new ArrayList<User>(usersToBeEmptied);
		for (final User currentProccessing : usersToBeEmptiedCopy) {
			this.lookupDeadUser(Integer.valueOf(currentProccessing.getOsceletonId()));
		}
		
		assert usersToBeEmptied.isEmpty() == true : "Remaining users: " + Arrays.toString(usersToBeEmptied.toArray());
	}
	
	private User newWaitingUser(final Integer osceletonUserId) {
		LOG.debug("-newWaitingUser(osceletonUserId=" + osceletonUserId + ")");
		final User newUser = this.factory.create(osceletonUserId.intValue());
		
		this.usersById.put(osceletonUserId, newUser);
		this.waitingUsers.add(newUser);
		
		return newUser;
	}

	private void dispatchWaitingUser(final User user) {
		LOG.debug("-dispatchWaitingUser(user=" + user + ")");
		
		System.out.println("XXXXXXXXXXXXXXXX dispatch waiting " + user);
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
