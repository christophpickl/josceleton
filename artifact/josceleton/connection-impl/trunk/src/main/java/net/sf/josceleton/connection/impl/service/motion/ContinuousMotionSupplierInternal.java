package net.sf.josceleton.connection.impl.service.motion;

import net.sf.josceleton.connection.api.service.motion.ContinuousMotionSupplier;
import net.sf.josceleton.connection.api.service.motion.MotionSupplierListener;
import net.sf.josceleton.connection.api.service.user.UserServiceListener;

interface ContinuousMotionSupplierInternal
	extends ContinuousMotionSupplier,
			MotionSupplierListener,
			UserServiceListener {
	
	/**
	 * Tries to get a <code>User</code> from the list of available processing users and attaches to it.
	 * 
	 * @since 0.5
	 */
	void initAttachingToUser();
	
}
