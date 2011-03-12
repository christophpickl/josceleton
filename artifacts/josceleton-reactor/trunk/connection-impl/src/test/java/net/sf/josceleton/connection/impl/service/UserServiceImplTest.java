package net.sf.josceleton.connection.impl.service;

import net.sf.josceleton.connection.api.service.TestableUserServiceDispatcher;
import net.sf.josceleton.connection.api.service.UserServiceTest;
import net.sf.josceleton.core.api.entity.User;
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

}
