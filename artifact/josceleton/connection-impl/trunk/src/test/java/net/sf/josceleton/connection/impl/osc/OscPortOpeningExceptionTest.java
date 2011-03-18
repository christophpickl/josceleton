package net.sf.josceleton.connection.impl.osc;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import net.sf.josceleton.commons.exception.JosceletonException;
import net.sf.josceleton.commons.test.AbstractExceptionTest;

import org.testng.annotations.Test;

@SuppressWarnings("boxing")
public class OscPortOpeningExceptionTest extends AbstractExceptionTest {

	@Test public final void newForInstantiation() {
		final int expectedPort = 42;
		final Throwable expectedCause = new Exception("foobar");
		
		final OscPortOpeningException exception = OscPortOpeningException.newByPort(expectedPort, expectedCause);
		
		assertThat(exception.getPort(), equalTo(expectedPort));
		assertThat(exception.getCause(), equalTo(expectedCause));
	}

	@Override protected final Class<? extends JosceletonException> getExceptionClass() {
		return OscPortOpeningException.class;
	}
	
}
