package net.sf.josceleton.core.impl.entity.location;

import net.sf.josceleton.commons.test.EqualsDescriptor;
import net.sf.josceleton.core.api.entity.CoordinateTest;
import net.sf.josceleton.core.api.entity.location.Coordinate;

public class CoordinateImplTest extends CoordinateTest {

	@Override protected final Coordinate createTestee(final float x, final float y, final float z) {
		return new CoordinateImpl(x, y, z);
	}

	@Override protected final Object createSameTesteeForEquals() {
		return new CoordinateImpl(0.0F, 0.4F, 0.2F);
	}

	@Override protected final EqualsDescriptor<Coordinate> createEqualsDescriptor() {
		final Coordinate sameA = this.createTestee(0.4F,  0.2F,  0.0F);
		final Coordinate sameB = this.createTestee(0.4F,  0.2F,  0.0F);
		final Coordinate differentC = this.createTestee(0.5F,  0.3F,  0.1F);
		final Coordinate differentD = this.createTestee(0.8F,  0.8F,  0.8F);
		
		return new EqualsDescriptor<Coordinate>(sameA, sameB, differentC, differentD);
	}

}
