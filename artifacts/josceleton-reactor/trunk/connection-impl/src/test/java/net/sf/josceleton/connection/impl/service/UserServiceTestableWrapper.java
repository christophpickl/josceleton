package net.sf.josceleton.connection.impl.service;

import java.util.Collection;

import net.sf.josceleton.connection.api.service.TestableUserServiceDispatcher;
import net.sf.josceleton.connection.api.service.UserServiceListener;
import net.sf.josceleton.core.api.entity.User;
import net.sf.josceleton.core.api.entity.UserState;

/**
 * @since 0.3
 */
class UserServiceTestableWrapper implements TestableUserServiceDispatcher {
	
	private final UserServiceImpl internal;
	
	public UserServiceTestableWrapper(final UserServiceImpl internal) {
		this.internal = internal;
	}
	
	/** {@inheritDoc} from {@link TestableUserServiceDispatcher} */
	@Override public final User delegateLookupJointMessage(final Integer osceletonUserId) {
		return this.internal.lookupUserForJointMessage(osceletonUserId); }

	/** {@inheritDoc} from {@link TestableUserServiceDispatcher} */
	@Override public final User delegateLookupUserMessage(final Integer osceletonUserId, final UserState userState) {
		return this.internal.lookupUserForUserMessage(osceletonUserId, userState); }

	/** {@inheritDoc} from {@link UserService} */
	@Override public final void addListener(final UserServiceListener listener) {
		this.internal.addListener(listener); }

	/** {@inheritDoc} from {@link UserService} */
	@Override public final void removeListener(final UserServiceListener listener) {
		this.internal.removeListener(listener); }

	/** {@inheritDoc} from {@link UserService} */
	@Override public final Collection<User> getProcessingUsers() {
		return this.internal.getProcessingUsers();
	}

	/** {@inheritDoc} from {@link UserService} */
	@Override public final Collection<User> getWaitingUsers() {
		return this.internal.getWaitingUsers();
	}
	
}
