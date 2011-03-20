package net.sf.josceleton.connection.api.service.motion;

import net.sf.josceleton.core.api.async.Async;

/**
 * Realizes an uninterruptible version of a traditional {@link MotionStream}.
 * 
 * &quot;<i>Uninterruptible</i>&quot; means no need of taking care about coming and going users,
 * just receive motion data from any available user in a continuously way.
 * 
 * @since 0.5
 */
public interface ContinuousMotionStream extends Async<MotionStreamListener> {
	
	// async communication only
	
}
