package net.sf.josceleton.core.api.entity;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import net.sf.josceleton.commons.test.AbstractEnumTest;
import net.sf.josceleton.core.api.entity.location.Coordinate;
import net.sf.josceleton.core.api.entity.location.Direction;

import org.jmock.Expectations;
import org.jmock.Mockery;
import org.testng.annotations.Test;

@SuppressWarnings("boxing")
public class DirectionTest extends AbstractEnumTest<Direction> {
	
	@Test
	public final void extractProperValues() {
		final float expectedX = 0.4F;
		final float expectedY = 0.3F;
		final float expectedZ = 4.2F;
		
		final Mockery mockery = new Mockery();
		final Coordinate mockedCoordinate = mockery.mock(Coordinate.class);
		mockery.checking(new Expectations() { {
			oneOf(mockedCoordinate).x(); will(returnValue(expectedX));
			oneOf(mockedCoordinate).y(); will(returnValue(expectedY));
			oneOf(mockedCoordinate).z(); will(returnValue(expectedZ));
		}});

		assertThat(Direction.X.extractValue(mockedCoordinate), equalTo(expectedX));
		assertThat(Direction.Y.extractValue(mockedCoordinate), equalTo(expectedY));
		assertThat(Direction.Z.extractValue(mockedCoordinate), equalTo(expectedZ));
		
		mockery.assertIsSatisfied();
	}
	
	@Test(expectedExceptions = NullPointerException.class)
	public final void extractValuesNullifiedFails() {
		Direction.X.extractValue(null);
	}

	@Override protected final EnumValuesDescriptor<Direction> getValuesDescriptor() {
		return new EnumValuesDescriptor<Direction>(
			Direction.class, Direction.X, Direction.Y, Direction.Z);
	}

	@Override protected final EnumValueOfDescriptor<Direction> getValueOfDescriptor() {
		return new EnumValueOfDescriptor<Direction>(Direction.class,
			"X", Direction.X,
			"Y", Direction.Y,
			"Z", Direction.Z
		);
	}

}
