package net.sf.josceleton;

import net.sf.josceleton.connection.api.Connection;
import net.sf.josceleton.connection.api.Connector;
import net.sf.josceleton.connection.api.service.motion.MotionStreamFactory;
import net.sf.josceleton.connection.impl.service.motion.ContinuousMotionStreamFactory;
import net.sf.josceleton.core.api.entity.location.Range;
import net.sf.josceleton.core.api.entity.location.RangeFactory;
import net.sf.josceleton.core.api.entity.location.RangeScaler;
import net.sf.josceleton.motion.api.gesture.GestureFactoryFacade;

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
	ContinuousMotionStreamFactory getContinuousMotionStreamFactory();

	/**
	 * @since 0.5
	 */
	RangeScaler getRangeScaler();

	/**
	 * @since 0.5
	 */
	Range newRange(float fromStart, float fromEnd, int toStart, int toEnd);

	/**
	 * @since 0.5
	 */
	RangeFactory getRangeFactory();

	/**
	 * @since 0.5
	 */
	MotionStreamFactory getMotionStreamFactory();
	
	// TODO HitWallGesture gesture = facade.create(HitWallGesture.class).propertyX(xValue).build();
	
	/**
	 * @since 0.5
	 */
	GestureFactoryFacade getGestureFactoryFacade();
}
