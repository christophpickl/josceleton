package net.sf.josceleton.josceleton;

import net.sf.josceleton.connection.api.Connection;
import net.sf.josceleton.connection.api.Connector;
import net.sf.josceleton.connection.impl.service.motion.ContinuousMotionSupplierFactory;
import net.sf.josceleton.core.api.entity.location.Range;
import net.sf.josceleton.core.api.entity.location.RangeScaler;

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
	
	/**
	 * @since 0.5
	 */
	Connector getConnector();

	/**
	 * @since 0.5
	 */
	ContinuousMotionSupplierFactory getContinuousMotionSupplierFactory();

	/**
	 * @since 0.5
	 */
	RangeScaler getRangeScaler();

	/**
	 * @since 0.5
	 */
	Range newRange(float fromStart, float fromEnd, int toStart, int toEnd);
	// HitWallGesture gesture = facade.create(HitWallGesture.class).propertyX(xValue).build();
	
}
