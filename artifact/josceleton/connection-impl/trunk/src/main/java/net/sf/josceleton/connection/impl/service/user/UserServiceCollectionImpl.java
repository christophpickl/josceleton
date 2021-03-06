package net.sf.josceleton.connection.impl.service.user;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import net.sf.josceleton.commons.collection.SafeLinkedHashSet;
import net.sf.josceleton.core.api.entity.user.User;

/**
 * @since 0.3
 */
class UserServiceCollectionImpl implements UserServiceCollection {

	private final Map<Integer, User> usersById = new HashMap<Integer, User>();
	
	private final Collection<User> waitingUsers = new SafeLinkedHashSet<User>();
	
	private final Collection<User> processingUsers = new SafeLinkedHashSet<User>();

	
	@Override public final void add(final User newUser) {
		this.usersById.put(Integer.valueOf(newUser.getOsceletonId()), newUser);
		this.waitingUsers.add(newUser);
	}

	@Override public final void moveToProcessing(final User processingUser) {
		this.waitingUsers.remove(processingUser);
		this.processingUsers.add(processingUser);
	}

	@Override public final User get(final Integer osceletonId) {
		return this.usersById.get(osceletonId);
	}

	@Override public final Collection<User> getProcessing() {
		return Collections.unmodifiableCollection(this.processingUsers);
	}

	@Override public final Collection<User> getWaiting() {
		return Collections.unmodifiableCollection(this.waitingUsers);
	}

	@Override public final void checkIfYetRegistered(
			final Integer osceletonUserId,
			final UserServiceCollectionResponder responder) {
		if(this.usersById.containsKey(osceletonUserId) == false) {
			return;
		}
		
		this.clearUsers(this.processingUsers, responder);
		this.clearUsers(this.waitingUsers, responder);
// MINOR assert this.usersById.isEmpty() : "usersById should be empty after fallback!";
	}

	
	@Override public final User remove(final Integer osceletonUserId, final UserServiceCollectionResponder responder) {
		final User removedStoredUser = this.usersById.remove(osceletonUserId);
		final boolean wasPreviouslyStored = removedStoredUser != null;
		
		final User userToDispatch;
		if(wasPreviouslyStored == true) {
			// simple case: user was already known to josceleton (was either waiting or processing)
			userToDispatch = removedStoredUser;
		} else {
			// this is an EXPECTED state; kinect detected user before josceleton started listening, but lost again.
			// artificially invoke one state step before
			userToDispatch = responder.lookupProcessingUser(osceletonUserId);
		}
		
		if(wasPreviouslyStored == true && this.waitingUsers.contains(removedStoredUser) == true) {
			this.waitingUsers.remove(removedStoredUser);
		} else {
			final User processingUserToRemove;
			if(wasPreviouslyStored == false) {
				processingUserToRemove = userToDispatch;
			} else {
				processingUserToRemove = removedStoredUser;
			}
			this.processingUsers.remove(processingUserToRemove);
		}
		
		return userToDispatch;
	}

	@Override
	public User getForJoint(final Integer osceletonUserId, final UserServiceCollectionResponder responder) {
		final User storedUser = this.get(osceletonUserId);
		if(storedUser != null) {
			return storedUser;
		}
		
		// artificially invoke one state step before; this is a common case.
		return responder.lookupProcessingUser(osceletonUserId);
	}

	/** Artificially drop out users (will dispatch events to listeners). */
	private void clearUsers(final Collection<User> usersToBeEmptied, final UserServiceCollectionResponder responder) {
		// avoid concurrent modification
		final Collection<User> usersToBeEmptiedCopy = new ArrayList<User>(usersToBeEmptied);
		
		for (final User currentProccessing : usersToBeEmptiedCopy) {
			// ignore result, as just want to fake artificial logout
			responder.lookupDeadUser(Integer.valueOf(currentProccessing.getOsceletonId()));
		}
// MINOR assert usersToBeEmptied.isEmpty() == true : "Remaining users: " + Arrays.toString(usersToBeEmptied.toArray());
	}

}
