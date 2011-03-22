package net.sf.josceleton.core.api.entity.location;

/**
 * Value object storing the raw coordinates as received by osceleton.
 * 
 * By default osceleton provides normalized coordinates, not millimeters or tha-like.
 * Normalization is done via:
 * <pre>
 * jointCoords[0] = off_x + (mult_x * (1280 - joint.position.X) / 2560);
 * jointCoords[1] = off_y + (mult_y * (960 - joint.position.Y) / 1920);
 * jointCoords[2] = off_z + (mult_z * joint.position.Z * 7.8125 / 10000);
 * </pre>
 * 
 * @since 0.1
 */
public interface Coordinate {

	/**
	 * @return X coordinate of the joint (if normalization is applied within range [0.0, 1.0])
	 * @since 0.1
	 */
	float x();
	
	/**
	 * @return Y coordinate of the joint (if normalization is applied within range [0.0, 1.0])
	 * @since 0.1
	 */
	float y();
	
	/**
	 * @return Z coordinate of the joint (if normalization is applied within range [0.0, 7.8125])
	 * @since 0.1
	 */
	float z();
	
}
