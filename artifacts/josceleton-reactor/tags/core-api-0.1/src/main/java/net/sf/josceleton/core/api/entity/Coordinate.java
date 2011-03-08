package net.sf.josceleton.core.api.entity;

/**
 * @since 0.1
 */
public interface Coordinate {

	/**
	 * @return X coordinate of joint in interval [0.0, 1.0].
	 * @since 0.1
	 */
	float x();
	
	/**
	 * @return Y coordinate of joint in interval [0.0, 1.0].
	 * @since 0.1
	 */
	float y();
	
	/**
	 * @return Z coordinate of joint in interval [0.0, 7.0].
	 * @since 0.1
	 */
	float z();
	
}
