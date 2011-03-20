package net.sf.josceleton.connection.impl.service.motion;

import net.sf.josceleton.connection.api.service.motion.MotionSupplier;
import net.sf.josceleton.connection.api.service.user.UsersCollection;

interface ContinuousMotionSupplierInternalFactory {
	
	ContinuousMotionSupplierInternal create(MotionSupplier supplier, UsersCollection users);
	
}
