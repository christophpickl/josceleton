package net.sf.josceleton.core.impl.entity.body;

import net.sf.josceleton.core.api.entity.Coordinate;
import net.sf.josceleton.core.api.entity.body.BodyPart;
import net.sf.josceleton.core.api.entity.body.Skeleton;

/**
 * @since 0.4
 */
public interface SkeletonInternal extends Skeleton {

	/**
	 * @since 0.4
	 */
	void update(BodyPart bodyPart, Coordinate coordinate);
	
}
