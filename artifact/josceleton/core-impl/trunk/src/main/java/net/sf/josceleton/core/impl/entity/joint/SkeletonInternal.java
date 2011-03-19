package net.sf.josceleton.core.impl.entity.joint;

import net.sf.josceleton.core.api.entity.joint.Joint;
import net.sf.josceleton.core.api.entity.joint.Skeleton;
import net.sf.josceleton.core.api.entity.location.Coordinate;

/**
 * @since 0.4
 */
public interface SkeletonInternal extends Skeleton {

	/**
	 * @since 0.4
	 */
	void update(Joint joint, Coordinate coordinate);
	
}
