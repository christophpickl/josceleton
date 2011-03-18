package net.sf.josceleton.core.impl.entity.joint;

import net.sf.josceleton.core.api.entity.Coordinate;
import net.sf.josceleton.core.api.entity.joint.Joint;
import net.sf.josceleton.core.api.entity.joint.Skeleton;

/**
 * @since 0.4
 */
public interface SkeletonInternal extends Skeleton {

	/**
	 * @since 0.4
	 */
	void update(Joint joint, Coordinate coordinate);
	
}
