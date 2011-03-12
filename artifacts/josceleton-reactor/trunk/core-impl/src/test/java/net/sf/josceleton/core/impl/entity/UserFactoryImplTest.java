package net.sf.josceleton.core.impl.entity;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import net.sf.josceleton.core.api.entity.User;

import org.testng.annotations.Test;

/**
 * @since 0.3
 */
public class UserFactoryImplTest {
	
	// checking to much stuff in here could lead to test internals of UserImpl
	
	@SuppressWarnings("boxing")
	@Test public final void testEverything() {
		int uniqueIdCounter = 1;
		
		final int expectedUser1Id = uniqueIdCounter++;
		final int expectedUser1OscId = 1;
		
		final int expectedUser2Id = uniqueIdCounter++;
		final int expectedUser2OscId = 1;
		
		final UserFactory factory = new UserFactoryImpl();
		final User user1 = factory.create(expectedUser1OscId);
		assertThat(user1.getId(), equalTo(expectedUser1Id));
		assertThat(user1.getOsceletonId(), equalTo(expectedUser1OscId));
		
		final User user2 = factory.create(expectedUser2OscId);
		assertThat(user2.getId(), equalTo(expectedUser2Id));
		assertThat(user2.getOsceletonId(), equalTo(expectedUser2OscId));
	}
	
	// LUXURY @TEST enhance UserFactoryImplTest (check illegal argument, ...)
	
}
