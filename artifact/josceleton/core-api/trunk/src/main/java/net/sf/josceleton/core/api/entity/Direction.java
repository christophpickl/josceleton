package net.sf.josceleton.core.api.entity;

/**
 * @since 0.1
 */
public enum Direction {

	/**
	 * @since 0.1
	 */
	X { @Override public float extractValue(final Coordinate coordinate) { return coordinate.x(); }},

	/**
	 * @since 0.1
	 */
	Y { @Override public float extractValue(final Coordinate coordinate) { return coordinate.y(); }},

	/**
	 * @since 0.1
	 */
	Z { @Override public float extractValue(final Coordinate coordinate) { return coordinate.z(); }};

	/**
	 * @since 0.1
	 */
	public abstract float extractValue(Coordinate coordinate);
	
}
