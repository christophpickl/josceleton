package net.sf.josceleton.connection.impl.service.user;

import java.util.Collection;

import net.sf.josceleton.connection.api.service.user.UserServiceListener;
import net.sf.josceleton.core.api.entity.user.User;
import net.sf.josceleton.core.api.entity.user.UserState;
import net.sf.josceleton.core.api.entity.user.UserStateCallback;
import net.sf.josceleton.core.impl.async.DefaultAsync;
import net.sf.josceleton.core.impl.entity.user.UserFactory;

import com.google.inject.Inject;

/**
 * @since 0.3
 */
class UserServiceImpl
	extends DefaultAsync<UserServiceListener>
		implements UserServiceInternal /* = UserService (UsersCollection), UserStore, UsersCollectionResponder */ {
	
	private final UserServiceCollection users;
	
	private final UserFactory factory;
	
	
	@Inject UserServiceImpl(final UserServiceCollection users, final UserFactory factory) {
		this.users = users;
		this.factory = factory;
	}

	/** {@inheritDoc} from {@link UserStore} */
	@Override public final User lookupUserForJointMessage(final Integer osceletonUserId) {
		return this.users.getForJoint(osceletonUserId, this);
	}

	/** {@inheritDoc} from {@link UserStore} */
	@SuppressWarnings("synthetic-access")
	@Override public final User lookupUserForUserMessage(final Integer osceletonUserId, final UserState userState) {
		return userState.callback(new UserStateCallback<User>() {
			@Override public User onStateWaiting() {
				UserServiceImpl.this.users.checkIfYetRegistered(osceletonUserId, UserServiceImpl.this);
				return UserServiceImpl.this.newWaitingUser(osceletonUserId);
			}
			@Override public User onStateProcessing() {
				return UserServiceImpl.this.lookupProcessingUser(osceletonUserId);
			}
			@Override public User onStateDead() {
				return UserServiceImpl.this.lookupDeadUser(osceletonUserId);
			}
		});
	}

	/** {@inheritDoc} from {@link UserServiceCollectionResponder} */
	public final User lookupDeadUser(final Integer osceletonUserId) {
		final User userToDispatch = this.users.remove(osceletonUserId, this);
		// userToDispatch can be null if entered in a running session
		for (final UserServiceListener listener : this.getListeners()) {
			listener.onUserDead(userToDispatch);
		}
		return userToDispatch;
	}

	/** {@inheritDoc} from {@link UserServiceCollectionResponder} */
	public User lookupProcessingUser(final Integer osceletonUserId) {
		final User storedUser = this.users.get(osceletonUserId);
		final User processingUser;
		if(storedUser == null) {
			// artificially invoke one state step before
			processingUser = this.newWaitingUser(osceletonUserId);
		} else {
			processingUser = storedUser;
		}
		
		this.users.moveToProcessing(processingUser);
		for (final UserServiceListener listener : this.getListeners()) {
			listener.onUserProcessing(processingUser);
		}
		return processingUser;
	}

	private User newWaitingUser(final Integer osceletonUserId) {
		final User newUser = this.factory.create(osceletonUserId.intValue());
		this.users.add(newUser);
		for (final UserServiceListener listener : this.getListeners()) {
			listener.onUserWaiting(newUser);
		}
		return newUser;
	}

	/** {@inheritDoc} from {@link UsersCollection} */
	@Override public final Collection<User> getProcessing() {
		return this.users.getProcessing();
	}

	/** {@inheritDoc} from {@link UsersCollection} */
	@Override public final Collection<User> getWaiting() {
		return this.users.getWaiting();
	}
	
}
