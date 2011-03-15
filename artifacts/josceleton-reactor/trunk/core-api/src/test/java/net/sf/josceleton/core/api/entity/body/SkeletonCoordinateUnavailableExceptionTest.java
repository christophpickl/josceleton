package net.sf.josceleton.core.api.entity.body;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.nullValue;
import net.sf.josceleton.commons.test.AbstractExceptionTest;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

public class SkeletonCoordinateUnavailableExceptionTest extends AbstractExceptionTest {

	@Override protected final Class<SkeletonCoordinateUnavailableException> getExceptionClass() {
		return SkeletonCoordinateUnavailableException.class;
	}
	
	@DataProvider(name = "provideForNewUnavailable")
	public final Object[][] provideForNewUnavailable() {
		return new Object[][] {
				new Object[] { Body.HEAD(), null },
				new Object[] { Body.HAND().LEFT(), new Exception("schuwab") }
		};
	}
	
	@Test(dataProvider = "provideForNewUnavailable")
	public final void testGetters(final BodyPart part, final Throwable cause) {
		final SkeletonCoordinateUnavailableException testee = cause == null ?
			SkeletonCoordinateUnavailableException.newUnavailable(part) :
			SkeletonCoordinateUnavailableException.newUnavailable(part, cause);
		
		if(cause == null) {
			assertThat(testee.getCause(), nullValue());
		} else {
			assertThat(testee.getCause(), equalTo(cause));
		}
		
		assertThat(testee.getPart(), equalTo(part));
	}

}
