package net.sf.josceleton.core.api.entity.message;

import net.sf.josceleton.core.api.entity.Coordinate;
import net.sf.josceleton.core.api.entity.body.BodyPart;

/**
 * @since 0.1
 */
public interface JointMessage extends GenericMessage {

	/**
	 * @since 0.1
	 */
	BodyPart getJointPart();

	/**
	 * @since 0.1
	 */
	Coordinate getCoordinate();
	
}
