package net.sf.josceleton.core.api.entity;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import net.sf.josceleton.commons.test.AbstractEnumTest;

import org.jmock.Expectations;
import org.jmock.Mockery;
import org.testng.annotations.Test;

@SuppressWarnings("boxing")
public class XyzDirectionTest extends AbstractEnumTest<XyzDirection> {
	
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

		assertThat(XyzDirection.X.extractValue(mockedCoordinate), equalTo(expectedX));
		assertThat(XyzDirection.Y.extractValue(mockedCoordinate), equalTo(expectedY));
		assertThat(XyzDirection.Z.extractValue(mockedCoordinate), equalTo(expectedZ));
		
		mockery.assertIsSatisfied();
	}
	
	@Test(expectedExceptions = NullPointerException.class)
	public final void extractValuesNullifiedFails() {
		XyzDirection.X.extractValue(null);
	}

	@Override protected final EnumValuesDescriptor<XyzDirection> getValuesDescriptor() {
		return new EnumValuesDescriptor<XyzDirection>(
			XyzDirection.class, XyzDirection.X, XyzDirection.Y, XyzDirection.Z);
	}

	@Override protected final EnumValueOfDescriptor<XyzDirection> getValueOfDescriptor() {
		return new EnumValueOfDescriptor<XyzDirection>(XyzDirection.class,
			"X", XyzDirection.X,
			"Y", XyzDirection.Y,
			"Z", XyzDirection.Z
		);
	}

}
