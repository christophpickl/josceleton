package net.sf.josceleton.connection.api.service;

import net.sf.josceleton.core.api.async.Listener;
import net.sf.josceleton.core.api.entity.Coordinate;
import net.sf.josceleton.core.api.entity.body.BodyPart;

/**
 * A per-user service to get notified about user movements, as well as stores most recent coordinates for all parts.
 * 
 * @since 0.3
 */
public interface MotionServiceListener extends Listener {
	
	/**
	 * LUXURY JAVADOC finish
	 * 
	 * Most likely both arguments will be ignored as listener preferes a full check of all relevant parts.
	 * 
	 * @param part which has been moved
	 * @param updatedCoordinate the body part has moved to
	 * @since 0.3
	 */
	void onMoved(BodyPart part, Coordinate updatedCoordinate);
	
}
