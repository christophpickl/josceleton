package net.sf.josceleton.connection.impl.service;

import net.sf.josceleton.connection.api.service.TestableUserServiceDispatcher;
import net.sf.josceleton.connection.api.service.UserService;
import net.sf.josceleton.connection.api.service.UserServiceListener;
import net.sf.josceleton.connection.api.service.UserServiceTest;
import net.sf.josceleton.core.api.entity.User;
import net.sf.josceleton.core.api.entity.UserState;
import net.sf.josceleton.core.impl.entity.UserFactory;

import org.jmock.Expectations;

public class UserServiceImplTest extends UserServiceTest {

	@Override protected final TestableUserServiceDispatcher createTestableTestee(final User[] expectedCreatedUsers) {
		final UserFactory mockedUserFactory = this.mock(UserFactory.class);
		
		this.checking(new Expectations() {{
			for (final User currentExpectedUser : expectedCreatedUsers) {
				// any internal unique ID
				oneOf(mockedUserFactory).create(with(currentExpectedUser.getOsceletonId()));
				will(returnValue(currentExpectedUser));
			}
		}});
		
		final UserServiceImpl service = new UserServiceImpl(mockedUserFactory);
		return new UserServiceTestableWrapper(service);
	}
	
	static class UserServiceTestableWrapper implements TestableUserServiceDispatcher {
		
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
		
	}

}
