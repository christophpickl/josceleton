package net.sf.josceleton.connection.api.service.motion;

import net.sf.josceleton.core.api.async.Listener;
import net.sf.josceleton.core.api.entity.Coordinate;
import net.sf.josceleton.core.api.entity.body.BodyPart;

/**
 * A per-user service to get notified about user movements, as well as stores most recent coordinates for all parts.
 * 
 * @since 0.4
 */
public interface MotionServiceListener extends Listener {
	
	/**
	 * LUXURY JAVADOC finish
	 * 
	 * Most likely both arguments will be ignored as listener preferes a full check of all relevant parts.
	 * 
	 * @param movedPart which has been, obviously, moved
	 * @param updatedCoordinate the body part has moved to
	 * @param skeleton as it is most likely one wants to recheck all conditions when single part moved
	 * @since 0.4
	 */
	void onMoved(BodyPart movedPart, Coordinate updatedCoordinate, Skeleton skeleton);
	
}
