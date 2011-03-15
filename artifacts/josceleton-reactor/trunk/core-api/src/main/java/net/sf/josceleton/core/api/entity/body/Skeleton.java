package net.sf.josceleton.core.api.entity.body;

import net.sf.josceleton.core.api.entity.Coordinate;

/**
 * @since 0.4
 */
public interface Skeleton {

	/**
	 * <font style="font-weight:bold;color:red;">ATTENTION:</font> This method might return <code>null</code>!
	 * 
	 * @param part of which to get the most recent coordinate
	 * @return recent position of given <code>part</code>, or </code>null</code> if none yet available
	 * @since 0.4
	 */
	Coordinate get(BodyPart part);
	
	/**
	 * Safely get a coordinate, ensuring the return value is not null but throwing an exception instead.
	 * 
	 * @param part of which to get the most recent coordinate
	 * @return recent position of given <code>part</code>
	 * @throws MotionCoordinateUnavailableException if no joint data was yet received for this body part
	 * @since 0.4
	 * @see #get(BodyPart)
	 */
	Coordinate getNullSafe(BodyPart part);
	
	/**
	 * 
	 * @param part
	 * @return
	 * @since 0.4
	 */
	boolean isCoordinateAvailable(BodyPart part);
	
}
