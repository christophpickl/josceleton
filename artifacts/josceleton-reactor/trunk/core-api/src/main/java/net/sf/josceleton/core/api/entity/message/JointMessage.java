package net.sf.josceleton.core.api.entity.message;

import net.sf.josceleton.core.api.entity.Coordinate;
import net.sf.josceleton.core.api.entity.User;
import net.sf.josceleton.core.api.entity.joint.Joint;

/**
 * @since 0.1
 */
public interface JointMessage extends GenericMessage {

	/**
	 * @since 0.1
	 */
	User getUser();
	
	/**
	 * @since 0.1
	 */
	Joint getJoint();

	/**
	 * @since 0.1
	 */
	Coordinate getCoordinate();
	
}
