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
 * Static variant of the <code>JosceletonFacade</code> type acting as a single point of entry.
 * 
 * It is highly recommended to NOT make use of this class. Only exception is quick hacky code...
 * Please see documentation on the website for further help about this topic.
 * 
 * @since 0.2
 * @see JosceletonFacade
 */
public final class Josceleton /* statically implements JosceletonFacade */ {
	
	private static /*non-final for testability*/ JosceletonFacade facade =
		new JosceletonFacadeImpl(JosceletonGuice.newInjector());
	
	private Josceleton() {
		// non instantiable, as just static methods available
	}

	/**
	 * @since 0.2
	 */
	public static Connection openConnection() {
		return Josceleton.facade.openConnection();
	}

	/**
	 * @since 0.2
	 */
	public static Connection openConnectionOnPort(final int port) {
		return Josceleton.facade.openConnectionOnPort(port);
	}
	
	/**
	 * @since 0.5
	 */
	public static Connector getConnector() {
		return Josceleton.facade.getConnector();
	}

	/**
	 * @since 0.5
	 */
	public static RangeScaler getRangeScaler() {
		return Josceleton.facade.getRangeScaler();
	}

	/**
	 * @since 0.5
	 */
	public static ContinuousMotionStreamFactory getContinuousMotionStreamFactory() {
		return Josceleton.facade.getContinuousMotionStreamFactory();
	}

	/**
	 * @since 0.5
	 */
	public static Range newRange(final float fromStart, final float fromEnd, final int toStart, final int toEnd) {
		return Josceleton.facade.newRange(fromStart, fromEnd, toStart, toEnd);
	}

	/**
	 * @since 0.5
	 */
	public static RangeFactory getRangeFactory() {
		return Josceleton.facade.getRangeFactory();
	}

	/**
	 * @since 0.5
	 */
	public static MotionStreamFactory getMotionStreamFactory() {
		return Josceleton.facade.getMotionStreamFactory();
	}

	/**
	 * @since 0.5
	 */
	public static GestureFactoryFacade getGestureFactoryFacade() {
		return Josceleton.facade.getGestureFactoryFacade();
	}
	
//	public static HitWallBuilder newHitWallBuilder() {
//	public static HitWallGesture newHitWall(HitWallConfig) {
	
}
