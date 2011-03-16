package net.sf.josceleton.core.api.entity.joint;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.nullValue;
import net.sf.josceleton.commons.test.AbstractExceptionTest;
import net.sf.josceleton.core.api.entity.joint.Joint;
import net.sf.josceleton.core.api.entity.joint.Joints;
import net.sf.josceleton.core.api.entity.joint.SkeletonCoordinateUnavailableException;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

public class SkeletonCoordinateUnavailableExceptionTest extends AbstractExceptionTest {

	@Override protected final Class<SkeletonCoordinateUnavailableException> getExceptionClass() {
		return SkeletonCoordinateUnavailableException.class;
	}
	
	@DataProvider(name = "provideForNewUnavailable")
	public final Object[][] provideForNewUnavailable() {
		return new Object[][] {
				new Object[] { Joints.HEAD(), null },
				new Object[] { Joints.HAND().LEFT(), new Exception("schuwab") }
		};
	}
	
	@Test(dataProvider = "provideForNewUnavailable")
	public final void testGetters(final Joint joint, final Throwable cause) {
		final SkeletonCoordinateUnavailableException testee = cause == null ?
			SkeletonCoordinateUnavailableException.newUnavailable(joint) :
			SkeletonCoordinateUnavailableException.newUnavailable(joint, cause);
		
		if(cause == null) {
			assertThat(testee.getCause(), nullValue());
		} else {
			assertThat(testee.getCause(), equalTo(cause));
		}
		
		assertThat(testee.getJoint(), equalTo(joint));
	}

}
