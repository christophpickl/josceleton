package net.sf.josceleton.core.api.test;

import net.sf.josceleton.core.api.entity.Coordinate;
import net.sf.josceleton.core.api.entity.Direction;

public class TestableCoordinate implements Coordinate {
	
	private final float x;
	
	private final float y;
	
	private final float z;

	
	protected TestableCoordinate(final float x, final float y, final float z) {
		this.x = x;
		this.y = y;
		this.z = z;
	}
	public static Coordinate newZeroed() {
		return new TestableCoordinate(0.0F, 0.0F, 0.0F);
	}
	
	public static Coordinate newWithX(final float x) {
		return new TestableCoordinate(x, 0.0F, 0.0F);
	}
	
	public static Coordinate newWithY(final float y) {
		return new TestableCoordinate(0.0F, y, 0.0F);
	}
	
	public static Coordinate newWithZ(final float z) {
		return new TestableCoordinate(0.0F, 0.0F, z);
	}

	public static Coordinate newWithXyz(final float x, final float y, final float z) {
		return new TestableCoordinate(x, y, z);
	}

	
	public static Coordinate newWithDirection(final Direction direction, final float value) {
		if(direction == Direction.X) {
			return TestableCoordinate.newWithX(value);
		} else if(direction == Direction.Y) {
			return TestableCoordinate.newWithY(value);
		} else if(direction == Direction.Z) {
			return TestableCoordinate.newWithZ(value);
		}
		throw new RuntimeException("can not happen! " + direction);
	}

	@Override public final float x() {
		return this.x;
	}

	@Override public final float y() {
		return this.y;
	}

	@Override public final float z() {
		return this.z;
	}

	@Override public final String toString() {
		return "TestableCoordinate[x=" + this.x + ", y=" + this.y + ", z=" + this.z + "]";
	}

}
