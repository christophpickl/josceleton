package net.sf.josceleton.connection.impl.service.user;

import net.sf.josceleton.connection.api.service.user.UserServiceTest;
import net.sf.josceleton.connection.api.test.TestableUserServiceDispatcher;
import net.sf.josceleton.connection.impl.service.user.UserServiceCollection;
import net.sf.josceleton.core.api.entity.User;
import net.sf.josceleton.core.impl.entity.UserFactory;

import org.jmock.Expectations;

public class UserServiceImplTest extends UserServiceTest {

	@Override protected final TestableUserServiceDispatcher createTestableTestee(final User[] expectedCreatedUsers) {
		final UserFactory mockedUserFactory = this.mock(UserFactory.class);
		
		this.checking(new Expectations() { {
			for (final User currentExpectedUser : expectedCreatedUsers) {
				// any internal unique ID
				oneOf(mockedUserFactory).create(with(currentExpectedUser.getOsceletonId()));
				will(returnValue(currentExpectedUser));
			}
		}});
		
		// MINOR @TEST we have kind of an integration test here, as UserService and UserServiceCollection is a mess
		final UserServiceCollection userCollection = new UserServiceCollectionImpl();
		final UserServiceImpl service = new UserServiceImpl(userCollection, mockedUserFactory);
		return new UserServiceTestableWrapper(service);
	}

}
