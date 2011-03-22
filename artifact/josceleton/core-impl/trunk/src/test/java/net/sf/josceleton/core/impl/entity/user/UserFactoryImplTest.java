package net.sf.josceleton.core.impl.entity.user;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import net.sf.josceleton.commons.test.jmock.AbstractMockeryTest;
import net.sf.josceleton.core.api.entity.user.User;
import net.sf.josceleton.core.api.entity.user.UserColorFactory;

import org.jmock.Expectations;
import org.testng.annotations.Test;

/**
 * @since 0.3
 */
public class UserFactoryImplTest extends AbstractMockeryTest {
	
	// checking to much stuff in here could lead to test internals of UserImpl
	
	@SuppressWarnings("boxing")
	@Test public final void testEverything() {
		int uniqueIdCounter = 1;
		
		final int expectedUser1Id = uniqueIdCounter++;
		final int expectedUser1OscId = 1;
		final int expectedUser1Color = 0xFF0000;
		
		final int expectedUser2Id = uniqueIdCounter++;
		final int expectedUser2OscId = 1;
		final int expectedUser2Color = 0x00FF00;
		
		final UserColorFactory colorFactory = mock(UserColorFactory.class);
		this.checking(new Expectations() { {
			oneOf(colorFactory).create(); will(returnValue(expectedUser1Color));
			oneOf(colorFactory).create(); will(returnValue(expectedUser2Color));
		}});
		
		final UserFactory factory = new UserFactoryImpl(colorFactory);
		final User user1 = factory.create(expectedUser1OscId);
		assertThat(user1.getUniqueId(), equalTo(expectedUser1Id));
		assertThat(user1.getOsceletonId(), equalTo(expectedUser1OscId));
		assertThat(user1.getColor(), equalTo(expectedUser1Color));
		
		final User user2 = factory.create(expectedUser2OscId);
		assertThat(user2.getUniqueId(), equalTo(expectedUser2Id));
		assertThat(user2.getOsceletonId(), equalTo(expectedUser2OscId));
		assertThat(user1.getColor(), equalTo(expectedUser2Color));
	}
	
	// LUXURY @TEST enhance UserFactoryImplTest (check illegal argument, ...)
	
}
