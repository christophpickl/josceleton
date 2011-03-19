package net.sf.josceleton.commons.exception;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.nullValue;
import net.sf.josceleton.commons.test.AbstractExceptionTest;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

public class InvalidArgumentExceptionTest extends AbstractExceptionTest {

	@Override protected final Class<? extends JosceletonException> getExceptionClass() {
		return InvalidArgumentException.class;
	}
	
	@DataProvider(name = "provideProperExceptionConstructorArguments")
	public final Object[][] provideProperExceptionConstructorArguments() {
		return new Object[][] {
			new Object[] { "myNonNullArgument", null, "myArgument != null" },
			new Object[] { "myIntArgument", Integer.valueOf(42), "myIntArgument < 21" },
		};
	}
	
	@SuppressWarnings("unchecked")
	@Test(dataProvider = "provideProperExceptionConstructorArguments")
	public final void justEverything(
			final String actualArgumentName,
			final Object actualArgumentValue,
			final String actualCondition) {
		
		final InvalidArgumentException exception = InvalidArgumentException.newInstance(
				actualArgumentName, actualArgumentValue, actualCondition);
		
		assertThat(exception.getMessage(), allOf(
			containsString(actualArgumentName),
			containsString(String.valueOf(actualArgumentValue)),
			containsString(actualCondition)
		));
		
		assertThat(exception.getArgumentName(), equalTo(actualArgumentName));
		assertThat(exception.getArgumentValue(), equalTo(actualArgumentValue));
		assertThat(exception.getCause(), nullValue());
	}
	
	@Test
	public final void newNotNullTest() {
		final String expectedArgumentName = "myArgName";
		final InvalidArgumentException exception = InvalidArgumentException.newNotNull(expectedArgumentName);
		
		assertThat(exception.getArgumentName(), equalTo(expectedArgumentName));
		assertThat(exception.getArgumentValue(), equalTo(null));
		
		assertThat(exception.getMessage(), containsString(expectedArgumentName));
		assertThat(exception.getCause(), equalTo(null));
	}
	
}
