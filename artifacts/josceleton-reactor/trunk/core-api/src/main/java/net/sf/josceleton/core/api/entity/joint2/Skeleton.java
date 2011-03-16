package net.sf.josceleton.core.api.entity.joint2;

import net.sf.josceleton.core.api.entity.Coordinate;

/**
 * @since 0.4
 */
public interface Skeleton {

	/**
	 * <font style="font-weight:bold;color:red;">ATTENTION:</font> This method might return <code>null</code>!
	 * 
	 * @param joint of which to get the most recent coordinate
	 * @return recent position of given <code>joint</code>, or </code>null</code> if none yet available
	 * @since 0.4
	 */
	Coordinate get(Joint joint);
	
	/**
	 * Safely get a coordinate, ensuring the return value is not null but throwing an exception instead.
	 * 
	 * @param joint of which to get the most recent coordinate
	 * @return recent position of given <code>joint</code>
	 * @throws SkeletonCoordinateUnavailableException if no joint data was yet received for this joint
	 * @since 0.4
	 * @see #get(Joint)
	 */
	Coordinate getNullSafe(Joint joint);
	
	/**
	 * 
	 * @param joint
	 * @return
	 * @since 0.4
	 */
	boolean isCoordinateAvailable(Joint joint);
	
}
