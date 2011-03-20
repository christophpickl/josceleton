package net.sf.josceleton.core.api.entity.location;


class RangeFactoryImpl implements RangeFactory {
	
	private static final float LOWER_LIMIT_XYZ = 0.0F;
	private static final float UPPER_LIMIT_Z = 7.0F;
	private static final float UPPER_LIMIT_XY = 1.0F;

	@Override
	public final Range createSpecific(final float fromStart, final float fromEnd, final int toStart, final int toEnd) {
		return new RangeImpl(fromStart, fromEnd, toStart, toEnd);
	}

	@Override
	public Range create(final Direction direction, final int toStart, final int toEnd) {
		final float fromEnd;
		if(direction == Direction.X || direction == Direction.Y) {
			fromEnd = UPPER_LIMIT_XY;
		} else {
			fromEnd = UPPER_LIMIT_Z;
		}
		
		return this.createSpecific(LOWER_LIMIT_XYZ, fromEnd, toStart, toEnd);
	}

}
