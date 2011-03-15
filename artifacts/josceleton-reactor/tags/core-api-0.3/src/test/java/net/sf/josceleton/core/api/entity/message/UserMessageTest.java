package net.sf.josceleton.core.api.entity.message;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import net.sf.josceleton.commons.test.EqualsDescriptor;
import net.sf.josceleton.commons.test.util.TestUtil;
import net.sf.josceleton.core.api.entity.User;
import net.sf.josceleton.core.api.entity.UserState;

import org.jmock.Mockery;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

public abstract class UserMessageTest extends AbstractMessageTest<UserMessage> {

	private User sameUser;
	private UserState sameUserState;
	private User differentUser;
	private UserState differentUserState;
	
	@BeforeMethod public final void setUpSameStuff() {
		final Mockery mockery = new Mockery();
		this.sameUser = mockery.mock(User.class, "sameUser");
		this.sameUserState = UserState.WAITING;
		
		this.differentUser = mockery.mock(User.class, "differentUser");
		this.differentUserState = UserState.PROCESSING;
	}
	
	protected abstract UserMessage createTestee(final User user, final UserState userState);
	
	/** {@inheritDoc} from {@link AbstractEqualsTest} */
	@Override protected final UserMessage createSameTesteeForEquals() {
		return this.createTestee(this.sameUser, this.sameUserState);
	}
	
	/** {@inheritDoc} from {@link AbstractEqualsTest} */
	@Override protected final EqualsDescriptor<UserMessage> createEqualsDescriptor() {
		final UserMessage sameA = this.createSameTesteeForEquals();
		final UserMessage sameB = this.createSameTesteeForEquals();
		
		final UserMessage differentC = this.createTestee(this.sameUser, this.differentUserState);
		final UserMessage differentD = this.createTestee(this.differentUser, this.sameUserState);
		final UserMessage differentE = this.createTestee(this.differentUser, this.differentUserState);
		
		return new EqualsDescriptor<UserMessage>(sameA, sameB, differentC, differentD, differentE);
	}

	@Test public final void simpleCreationAndGetterTestForJoint() {
		final Mockery mockery = new Mockery();
		
		final User mockedUser = mockery.mock(User.class);
		final UserState expectedUserState = UserState.DEAD;
		
		final UserMessage message = this.createTestee(mockedUser, expectedUserState);
		
		assertThat(message.getUser(), equalTo(mockedUser));
		assertThat(message.getUserState(), equalTo(expectedUserState));
		
		mockery.assertIsSatisfied();
	}
	
	@Test public final void testToString() {
		final Mockery mockery = new Mockery(); // {{ setImposteriser(ClassImposteriser.INSTANCE); }};
		final User mockedUser = mockery.mock(User.class);
		final UserState expectedUserState = UserState.DEAD;
		final UserMessage message = this.createTestee(mockedUser, expectedUserState);
		TestUtil.assertObjectToString(message, mockedUser.toString(), expectedUserState.toString()); 
		
		mockery.assertIsSatisfied();
	}
	
}
