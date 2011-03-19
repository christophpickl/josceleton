package net.sf.josceleton.core.impl.entity.joint;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.nullValue;
import net.sf.josceleton.commons.test.jmock.AbstractMockeryTest;
import net.sf.josceleton.core.api.entity.Coordinate;
import net.sf.josceleton.core.api.entity.joint.Joint;
import net.sf.josceleton.core.api.entity.joint.Joints;
import net.sf.josceleton.core.api.entity.joint.SkeletonCoordinateUnavailableException;
import net.sf.josceleton.core.api.entity.joint.JointParts.Head;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

@SuppressWarnings("boxing")
public class SkeletonImplTest extends AbstractMockeryTest {
	
	private SkeletonInternal skeleton;
	
	@BeforeMethod public final void setUpSkeleton() {
		this.skeleton = new SkeletonImpl();
	}
	@AfterMethod public final void tearDownSkeleton() {
		this.skeleton = null;
	}
	
	@Test(expectedExceptions = SkeletonCoordinateUnavailableException.class,
			expectedExceptionsMessageRegExp = ".*Head.*")
	public final void getNullSafeFails() {
		this.skeleton.getNullSafe(Joints.HEAD());
	}
	
	@Test public final void getNullSafeCorrectly() {
		final Head joint = Joints.HEAD();
		final Coordinate mockedCoord = this.mock(Coordinate.class);
		this.skeleton.update(joint, mockedCoord);
		assertThat(this.skeleton.getNullSafe(joint), is(mockedCoord));
	}
	
	
	@Test public final void isCoordinateAvailableCorrectly() {
		final Head joint = Joints.HEAD();
		
		assertThat(this.skeleton.isCoordinateAvailable(joint), equalTo(false));
		
		this.skeleton.update(joint, this.mock(Coordinate.class));
		assertThat(this.skeleton.isCoordinateAvailable(joint), equalTo(true));
	}
	
	@Test public final void getReturnsNull() {
		assertThat(this.skeleton.get(Joints.HEAD()), is(nullValue()));
	}

	@Test
	public final void updateCoordinates() {
		final Joint joint = Joints.SHOULDER().LEFT();
		final Coordinate coord1 = this.mock(Coordinate.class, "coord1");
		assertThat(this.skeleton.get(joint), is(nullValue()));
		
		this.skeleton.update(joint, coord1);
		assertThat(this.skeleton.get(joint), is(coord1));
		
		final Coordinate coord2 = this.mock(Coordinate.class, "coord2");
		this.skeleton.update(joint, coord2);
		
		assertThat(this.skeleton.get(joint), is(coord2));
	}
	
}
