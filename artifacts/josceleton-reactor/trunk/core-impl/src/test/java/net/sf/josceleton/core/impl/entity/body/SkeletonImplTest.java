package net.sf.josceleton.core.impl.entity.body;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.nullValue;
import net.sf.josceleton.commons.test.jmock.AbstractMockeryTest;
import net.sf.josceleton.core.api.entity.Coordinate;
import net.sf.josceleton.core.api.entity.body.Body;
import net.sf.josceleton.core.api.entity.body.BodyPart;
import net.sf.josceleton.core.api.entity.body.SkeletonCoordinateUnavailableException;
import net.sf.josceleton.core.api.entity.body.BodyParts.Head;

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
		this.skeleton.getNullSafe(Body.HEAD());
	}
	
	@Test public final void getNullSafeCorrectly() {
		final Head part = Body.HEAD();
		final Coordinate mockedCoord = this.mock(Coordinate.class);
		this.skeleton.update(part, mockedCoord);
		assertThat(this.skeleton.getNullSafe(part), is(mockedCoord));
	}
	
	
	@Test public final void isCoordinateAvailableCorrectly() {
		final Head part = Body.HEAD();
		
		assertThat(this.skeleton.isCoordinateAvailable(part), equalTo(false));
		
		this.skeleton.update(part, this.mock(Coordinate.class));
		assertThat(this.skeleton.isCoordinateAvailable(part), equalTo(true));
	}
	
	@Test public final void getReturnsNull() {
		assertThat(this.skeleton.get(Body.HEAD()), is(nullValue()));
	}

	@Test
	public final void updateCoordinates() {
		final BodyPart part = Body.SHOULDER().LEFT();
		final Coordinate coord1 = this.mock(Coordinate.class, "coord1");
		assertThat(this.skeleton.get(part), is(nullValue()));
		
		this.skeleton.update(part, coord1);
		assertThat(this.skeleton.get(part), is(coord1));
		
		final Coordinate coord2 = this.mock(Coordinate.class, "coord2");
		this.skeleton.update(part, coord2);
		
		assertThat(this.skeleton.get(part), is(coord2));
	}
	
}
