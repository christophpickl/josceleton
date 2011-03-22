package net.sf.josceleton.core.api.entity.joint;

import net.sf.josceleton.core.api.entity.joint.JointParts.LeftJoint;
import net.sf.josceleton.core.api.entity.joint.JointParts.RightJoint;
import net.sf.josceleton.core.api.entity.location.Coordinate;

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
	Coordinate getNullSafe(Joint joint); // FIXME rename "getNullSafe" to simply "get", and old "get" to something like "getOrNull"
	
	/**
	 * 
	 * @param joint
	 * @return
	 * @since 0.4
	 * @deprecated since 0.5; see {@link #isAvailableFor(Joint...)} instead.
	 */
	@Deprecated
	boolean isCoordinateAvailable(Joint joint);
	
	/**
	 * @since 0.5
	 */
	boolean isAvailableFor(Joint... joint);
	
	/**
	 * Allows to pass symmetric joint parts, e.g.: <code>Joints.HANDS()</code>.
	 * @since 0.5
	 */
	<J extends Joint, SJ extends SymetricJoint<J, LJ, RJ>, LJ extends LeftJoint<J>, RJ extends RightJoint<J>> boolean
		areAvailableFor(SJ symetricJoint);
	
	/**
	 * @return true if there is data for all joints available.
	 * @since 0.5
	 */
	boolean allCoordinatesAvailable();
}
