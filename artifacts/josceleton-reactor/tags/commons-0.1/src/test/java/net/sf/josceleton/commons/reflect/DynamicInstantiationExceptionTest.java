package net.sf.josceleton.commons.reflect;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import net.sf.josceleton.commons.exception.JosceletonException;
import net.sf.josceleton.commons.test.AbstractExceptionTest;

import org.testng.annotations.Test;

@SuppressWarnings("boxing")
public class DynamicInstantiationExceptionTest extends AbstractExceptionTest {

	@Test public final void newForInstantiation() {
		final ClassAdapter<?> expectedClazz = ClassAdapterImpl.create(DynamicInstantiationExceptionTest.class);
		final Object[] expectedArguments = new Object[] { "arg" };
		final Throwable expectedCause = new Exception("foobar");
		
		final DynamicInstantiationException exception = DynamicInstantiationException.newForInstantiation(
				expectedClazz, expectedArguments, expectedCause);
		
		assertThat(exception.getArguments(), equalTo(expectedArguments));
		assertThat(exception.getClazz() == expectedClazz, equalTo(true));
	}
	
	@Test public final void newForNotFoundConstructor() {
		final ClassAdapter<?> expectedClazz = ClassAdapterImpl.create(DynamicInstantiationExceptionTest.class);
		final Object[] expectedArguments = new Object[] { "arg" };
		
		final DynamicInstantiationException exception = DynamicInstantiationException.newForNotFoundConstructor(
			expectedClazz, expectedArguments);
		
		assertThat(exception.getArguments(), equalTo(expectedArguments));
		assertThat(exception.getClazz() == expectedClazz, equalTo(true));
	}

	@Override protected final Class<? extends JosceletonException> getExceptionClass() {
		return DynamicInstantiationException.class;
	}
	
}
