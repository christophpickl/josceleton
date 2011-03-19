package net.sf.josceleton.core.api.entity;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import net.sf.josceleton.commons.test.AbstractEnumTest;
import net.sf.josceleton.core.api.entity.user.UserState;
import net.sf.josceleton.core.api.entity.user.UserStateCallback;

import org.jmock.Expectations;
import org.jmock.Mockery;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

public class UserStateTest extends AbstractEnumTest<UserState> {

	@Override protected final EnumValueOfDescriptor<UserState> getValueOfDescriptor() {
		return new EnumValueOfDescriptor<UserState>(UserState.class,
			"WAITING", UserState.WAITING, "PROCESSING", UserState.PROCESSING, "DEAD", UserState.DEAD);
	}

	@Override protected final EnumValuesDescriptor<UserState> getValuesDescriptor() {
		return new EnumValuesDescriptor<UserState>(UserState.class,
			UserState.WAITING, UserState.PROCESSING, UserState.DEAD);
	}
	
	@DataProvider(name = "provideAllUserStates")
	public final Object[][] provideAllUserStates() {
		final UserState[] states = UserState.values();
		final Object[][] result = new Object[states.length][];
		for (int i = 0; i < result.length; i++) {
			result[i] = new Object[] { states[i] };
		}
		return result;
	}
	@Test(dataProvider = "provideAllUserStates")
	public final void testCallback(final UserState state) {
		this.assertCallback(state);
	}
	
	@Test(expectedExceptions = NullPointerException.class, dataProvider = "provideAllUserStates")
	public final void passingNullForCallbackFails(final UserState state) {
		state.callback(null);
	}
	
	
	@SuppressWarnings("unchecked")
	private void assertCallback(final UserState state) {
		final String expectedResult = "some passed through value";
		final Mockery mockery = new Mockery();
		final UserStateCallback<String> mockedCallback = mockery.mock(UserStateCallback.class);
		mockery.checking(new Expectations() { {
			if(state == UserState.WAITING) {
				oneOf(mockedCallback).onStateWaiting();
			} else if(state == UserState.PROCESSING) {
				oneOf(mockedCallback).onStateProcessing();
			} else if(state == UserState.DEAD) {
				oneOf(mockedCallback).onStateDead();
			} else {
				throw new RuntimeException("Unhandled state: " + state); // can not happen
			}
			will(returnValue(expectedResult));
		}});
		
		final String actualResult = state.callback(mockedCallback);
		
		assertThat(actualResult, equalTo(expectedResult));
		mockery.assertIsSatisfied();
	}
}
