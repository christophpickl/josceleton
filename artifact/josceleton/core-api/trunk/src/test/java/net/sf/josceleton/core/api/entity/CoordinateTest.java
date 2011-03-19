package net.sf.josceleton.core.api.entity;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.not;
import net.sf.josceleton.commons.exception.InvalidArgumentException;
import net.sf.josceleton.commons.test.AbstractEqualsTest;
import net.sf.josceleton.commons.test.util.TestUtil;
import net.sf.josceleton.core.api.entity.location.Coordinate;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

@SuppressWarnings("boxing")
public abstract class CoordinateTest extends AbstractEqualsTest<Coordinate> {

	protected abstract Coordinate createTestee(float x, float y, float z);
	
	
	@DataProvider(name = "provideIllegalConstructorArguments")
	public final Object[][] provideIllegalConstructorArguments() {
		return new Object[][] {
			new Object[] { -0.1F,  0.0F,  0.0F },
			new Object[] {  0.0F, -0.1F,  0.0F },
			new Object[] {  0.0F,  0.0F, -0.1F },
			
			new Object[] {  1.1F,  1.0F,  7.0F },
			new Object[] {  1.0F,  1.1F,  7.0F },
			new Object[] {  1.0F,  1.0F,  7.1F }
		};
	} // MINOR ??? COORDINATE-RANGE re-enable test for: coordinate passing out-of-range constructor arguments
	@Test(enabled = false, expectedExceptions = InvalidArgumentException.class,
			dataProvider = "provideIllegalConstructorArguments")
	public final void passingIllegalConstructorArgumentsFails(final float x, final float y, final float z) {
		this.createTestee(x, y, z);
	}
	
	
	@DataProvider(name = "provideValidConstructorArguments")
	public final Object[][] provideValidConstructorArguments() {
		return new Object[][] {
			new Object[] {  0.0F,  0.0F,  0.0F },
			new Object[] {  1.0F,  1.0F,  7.0F },
			new Object[] {  0.2F,  0.1F,  4.2F }
		};
	}
	@Test(dataProvider = "provideValidConstructorArguments")
	public final void validConstructorArgumentsAndGettersReturnSame(final float x, final float y, final float z) {
		final Coordinate actualCoordinate = this.createTestee(x, y, z);
		
		assertThat(actualCoordinate.x(), equalTo(x));
		assertThat(actualCoordinate.y(), equalTo(y));
		assertThat(actualCoordinate.z(), equalTo(z));
	}
	
	@DataProvider(name = "provideEqualsCoordinates")
	public final Object[][] provideEqualsCoordinates() {
		return new Object[][] {
			new Object[] { true,  0.0F, 0.0F, 0.0F },
			
			new Object[] { false, 1.0F, 0.0F, 0.0F },
			new Object[] { false, 0.0F, 1.0F, 0.0F },
			new Object[] { false, 0.0F, 0.0F, 1.0F },
			
			new Object[] { false, 0.0F, 1.0F, 1.0F },
			new Object[] { false, 1.0F, 0.0F, 1.0F },
			new Object[] { false, 1.0F, 1.0F, 0.0F },

			new Object[] { false, 1.0F, 1.0F, 1.0F }
		};
	}
	
	@Test(dataProvider = "provideEqualsCoordinates")
	public final void testComplexEquals(final boolean shouldBeEqual, final float x2, final float y2, final float z2) {
		final Coordinate coord1 = this.createTestee(0.0F, 0.0F, 0.0F);
		final Coordinate coord2 = this.createTestee(x2, y2, z2);
		
		if(shouldBeEqual) {
			assertThat(coord1, equalTo(coord2));
		} else {
			assertThat(coord1, not(equalTo(coord2)));
		}
	}
	
	@Test
	public final void testToString() {
		final Coordinate u1 = this.createTestee(0.5F,  0.0F,  0.0F);
		TestUtil.assertObjectToString(u1, "0.5", "0.0"); 
	}
}
