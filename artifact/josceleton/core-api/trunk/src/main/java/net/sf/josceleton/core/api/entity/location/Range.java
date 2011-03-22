package net.sf.josceleton.core.api.entity.location;

/**
 * @since 0.5
 */
public interface Range { // FIXME rename "Range" to "Scaling"
	
	// LUXURY easeIn/Out-function = { LINEAR, EXPONENTIAL, CUBIC, QUADRATIC }
	
	/**
	 * @since 0.5
	 */
	float getFromStart();

	/**
	 * @since 0.5
	 */
	float getFromEnd();

	/**
	 * @since 0.5
	 */
	int getToStart();

	/**
	 * @since 0.5
	 */
	int getToEnd();
	
}
