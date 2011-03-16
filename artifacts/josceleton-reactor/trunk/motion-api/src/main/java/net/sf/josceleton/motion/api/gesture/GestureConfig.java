package net.sf.josceleton.motion.api.gesture;

import java.util.Collection;

import net.sf.josceleton.core.api.entity.joint.Joint;

/**
 * @since 0.4
 */
public interface GestureConfig {
	
	Collection<Joint> getRelevantJoints();
}
