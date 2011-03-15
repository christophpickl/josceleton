package net.sf.josceleton.connection.api.service;

import net.sf.josceleton.core.api.async.Async;
import net.sf.josceleton.core.api.entity.Coordinate;
import net.sf.josceleton.core.api.entity.body.BodyPart;

/**
 * @since 0.3
 */
public interface MotionService extends Async<MotionServiceListener> {

	/**
	 * <font style="font-weight:bold;color:red;">ATTENTION:</font> This method might return <code>null</code>!
	 * 
	 * @param part of which to get the most recent coordinate
	 * @return recent position of given <code>part</code>, or </code>null</code> if none yet available
	 * @since 0.3
	 */
	Coordinate get(BodyPart part);
	
	/**
	 * Safely get a coordinate, ensuring the return value is not null but throwing an exception instead.
	 * 
	 * @param part of which to get the most recent coordinate
	 * @return recent position of given <code>part</code>
	 * @throws MotionCoordinateUnavailableException if no joint data was yet received for this body part
	 * @since 0.3
	 * @see #get(BodyPart)
	 */
	Coordinate getNullSafe(BodyPart part);
	
	/**
	 * 
	 * @param part
	 * @return
	 * @since 0.3
	 */
	boolean isCoordinateAvailable(BodyPart part);
	
}
