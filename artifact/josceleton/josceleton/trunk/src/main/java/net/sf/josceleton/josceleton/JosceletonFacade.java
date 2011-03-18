package net.sf.josceleton.josceleton;

import net.sf.josceleton.connection.api.Connection;

/**
 * @since 0.2
 */
public interface JosceletonFacade {

	/**
	 * @since 0.2
	 */
	Connection openConnection();

	/**
	 * @since 0.2
	 */
	Connection openConnectionOnPort(int port);
	
	// HitWallGesture gesture = facade.create(HitWallGesture.class).propertyX(xValue).build();
	
}
